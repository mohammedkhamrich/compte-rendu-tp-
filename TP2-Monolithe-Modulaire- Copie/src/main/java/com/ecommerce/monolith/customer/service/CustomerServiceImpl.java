package com.ecommerce.monolith.customer.service;

import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.dto.CustomerDTO;
import com.ecommerce.monolith.customer.mapper.CustomerMapper;
import com.ecommerce.monolith.customer.model.Customer;
import com.ecommerce.monolith.customer.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(Long id) {
        Customer c = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
        return mapper.toDTO(c);
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        return mapper.toDTO(repository.save(mapper.toEntity(request)));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CreateCustomerRequest request) {
        Customer c = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
        mapper.updateEntity(request, c);
        return mapper.toDTO(repository.save(c));
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!repository.existsById(id))
            throw new EntityNotFoundException("Customer not found: " + id);
        repository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}