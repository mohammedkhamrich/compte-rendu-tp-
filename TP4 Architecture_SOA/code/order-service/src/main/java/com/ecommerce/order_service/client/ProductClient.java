package com.ecommerce.order_service.client;

import com.ecommerce.order_service.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable Long id);

    @PutMapping("/api/products/{id}/stock")
    ProductDTO updateStock(@PathVariable Long id, @RequestParam Integer quantity);
}