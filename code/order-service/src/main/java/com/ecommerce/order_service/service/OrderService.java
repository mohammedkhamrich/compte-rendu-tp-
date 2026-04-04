package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.ProductClient;
import com.ecommerce.order_service.dto.ProductDTO;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public List<Order> getAllOrders() { return orderRepository.findAll(); }

    public Order createOrder(Long productId, Integer quantity) {
        ProductDTO product = productClient.getProductById(productId);
        if (product.getStock() < quantity)
            throw new RuntimeException("Stock insuffisant");
        Order order = new Order();
        order.setProductId(productId);
        order.setProductName(product.getName());
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice() * quantity);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        productClient.updateStock(productId, quantity);
        return orderRepository.save(order);
    }
}