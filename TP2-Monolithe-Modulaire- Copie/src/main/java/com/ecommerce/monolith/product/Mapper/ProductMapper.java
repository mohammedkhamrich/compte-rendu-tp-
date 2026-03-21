package com.ecommerce.monolith.product.mapper;

import com.ecommerce.monolith.product.dto.CreateProductRequest;
import com.ecommerce.monolith.product.dto.ProductDTO;
import com.ecommerce.monolith.product.model.Product;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    List<ProductDTO> toDTOList(List<Product> products);
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntity(CreateProductRequest request, @MappingTarget Product product);
}