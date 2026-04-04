package com.ecommerce.monolith.order.mapper;

import com.ecommerce.monolith.order.dto.OrderDTO;
import com.ecommerce.monolith.order.model.Order;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
    List<OrderDTO> toDTOList(List<Order> orders);
}