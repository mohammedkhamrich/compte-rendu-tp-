package com.ecommerce.monolith.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String phone;
}