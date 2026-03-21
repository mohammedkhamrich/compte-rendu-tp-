package com.ecommerce.monolith.order.service;

import com.ecommerce.monolith.customer.service.CustomerService;
import com.ecommerce.monolith.order.dto.CreateOrderRequest;
import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.mapper.OrderMapper;
import com.ecommerce.monolith.order.model.Order;
import com.ecommerce.monolith.order.repository.OrderRepository;
import com.ecommerce.monolith.product.dto.ProductDTO;
import com.ecommerce.monolith.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final CustomerService customerService;

    @Override
    public OrderDTO createOrder(CreateOrderRequest request) {
        if (!customerService.existsById(request.getCustomerId()))
            throw new EntityNotFoundException("Customer not found: " + request.getCustomerId());

        ProductDTO product = productService.getProductById(request.getProductId());

        BigDecimal total = product.getPrice()
            .multiply(BigDecimal.valueOf(request.getQuantity()));

        Order order = Order.builder()
            .customerId(request.getCustomerId())
            .productId(request.getProductId())
            .quantity(request.getQuantity())
            .totalPrice(total)
            .build();

        return orderMapper.toDTO(orderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        return orderMapper.toDTOList(orderRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByCustomer(Long customerId) {
        if (!customerService.existsById(customerId))
            throw new EntityNotFoundException("Customer not found: " + customerId);
        return orderMapper.toDTOList(orderRepository.findByCustomerId(customerId));
    }
}