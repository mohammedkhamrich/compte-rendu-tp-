package com.ecommerce.monolith.order.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateOrderRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull
    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;
}