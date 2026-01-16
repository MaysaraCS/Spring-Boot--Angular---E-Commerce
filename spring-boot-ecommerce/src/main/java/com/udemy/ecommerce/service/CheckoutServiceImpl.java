package com.udemy.ecommerce.service;

import com.udemy.ecommerce.dto.Purchase;
import com.udemy.ecommerce.dto.PurchaseResponse;
import com.udemy.ecommerce.entity.Customer;
import com.udemy.ecommerce.entity.Order;
import com.udemy.ecommerce.entity.OrderItem;
import com.udemy.ecommerce.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    //@Autowired is optional here since we only have one constructor
    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrive the order from the database
        Order order = purchase.getOrder();

        // generate a tracking number for the order
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with order items
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billing address and shipping address
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

        // save the order to the database
        customerRepository.save(customer);

        // return response
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID tracking number

        return UUID.randomUUID().toString();
    }
}
