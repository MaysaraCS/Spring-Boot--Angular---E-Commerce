package com.udemy.ecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.udemy.ecommerce.dto.PaymentInfo;
import com.udemy.ecommerce.dto.Purchase;
import com.udemy.ecommerce.dto.PurchaseResponse;
import com.udemy.ecommerce.entity.Customer;
import com.udemy.ecommerce.entity.Order;
import com.udemy.ecommerce.entity.OrderItem;
import com.udemy.ecommerce.entity.Address;
import com.udemy.ecommerce.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               @Value("${stripe.key.secret}") String secretKey) {
        this.customerRepository = customerRepository;

        // initialize Stripe API with secret key
        Stripe.apiKey = secretKey;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve the order from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // get addresses from purchase
        Address shippingAddress = purchase.getShippingAddress();
        Address billingAddress = purchase.getBillingAddress();

        // SOLUTION: Check if billing and shipping addresses are the same
        // If they are the same, use the same reference instead of creating duplicates
        if (addressesAreEqual(shippingAddress, billingAddress)) {
            // Use the same address object for both to prevent duplication
            order.setShippingAddress(shippingAddress);
            order.setBillingAddress(shippingAddress); // Same reference
        } else {
            // Different addresses, set them separately
            order.setShippingAddress(shippingAddress);
            order.setBillingAddress(billingAddress);
        }

        // populate customer with order
        Customer customer = purchase.getCustomer();

        // check if this customer already exists
        String theEmail = customer.getEmail();

        Customer customerFromDB = customerRepository.findByEmail(theEmail);
        if(customerFromDB != null) {
            // if u find the customer then let's assign them
            customer = customerFromDB;
        }
        customer.add(order);

        // save to database (cascading will handle addresses)
        customerRepository.save(customer);

        // return response
        return new PurchaseResponse(orderTrackingNumber);
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "Luv2Shop purchase");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }

    /**
     * Helper method to check if two addresses are equal based on content
     * @param address1 First address to compare
     * @param address2 Second address to compare
     * @return true if addresses have same content, false otherwise
     */
    private boolean addressesAreEqual(Address address1, Address address2) {
        if (address1 == null && address2 == null) {
            return true;
        }
        if (address1 == null || address2 == null) {
            return false;
        }

        // Compare all address fields
        return address1.equals(address2);
    }
}