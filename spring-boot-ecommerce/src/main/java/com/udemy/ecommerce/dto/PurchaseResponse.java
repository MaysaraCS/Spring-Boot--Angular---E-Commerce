package com.udemy.ecommerce.dto;

import lombok.Data;
import lombok.NonNull;

// @Data will generate a constructor for final fields
@Data
public class PurchaseResponse {

    //@NonNull
    private final String orderTrackingNumber;
}
