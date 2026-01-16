package com.udemy.ecommerce.service;

import com.udemy.ecommerce.dto.Purchase;
import com.udemy.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
