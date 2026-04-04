package com.ecommerce.monolith.product.service;

import com.ecommerce.monolith.product.dto.CreateProductRequest;
import com.ecommerce.monolith.product.dto.ProductDTO;
import com.ecommerce.monolith.product.mapper.ProductMapper;
import com.ecommerce.monolith.product.model.Product;
import com.ecommerce.monolith.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        return mapper.toDTO(product);
    }

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = mapper.toEntity(request);
        return mapper.toDTO(repository.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, CreateProductRequest request) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        mapper.updateEntity(request, product);
        return mapper.toDTO(repository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Product not found: " + id);
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}