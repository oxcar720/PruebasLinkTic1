package com.oxcar720.products_service.service;

import com.oxcar720.products_service.dto.*;
import com.oxcar720.products_service.entity.ProductEntity;
import com.oxcar720.products_service.exception.BusinessException;
import com.oxcar720.products_service.mapper.ProductMapper;
import com.oxcar720.products_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductDTO create(CreateProductRequest request) {

        repository.findBySku(request.getSku())
                .ifPresent(p -> {
                    throw new BusinessException("SKU already exists");
                });
        try{
            ProductEntity entity = mapper.toEntity(request);
            entity.setId(UUID.randomUUID());

            ProductEntity saved = repository.save(entity);

            return mapper.toDTO(saved);

        }catch (DataIntegrityViolationException e) {
            throw new BusinessException("SKU already exists");
        }

    }

    public Page<ProductDTO> search(
            String status,
            String search,
            int page,
            int size,
            String sort
    ) {

        ProductEntity.Status statusEnum = null;

        if (status != null && !status.isBlank()) {
            try {
                statusEnum = ProductEntity.Status.valueOf(status.toUpperCase());
            } catch (Exception e) {
                throw new BusinessException("Invalid status value");
            }
        }

        Sort sortObj = Sort.by("createdAt").descending();

        if (sort != null && sort.contains(",")) {
            String[] parts = sort.split(",");
            if (parts.length == 2) {
                sortObj = Sort.by(
                    parts[1].equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC,
                    parts[0]
                );
            }
        }

        Pageable pageable = PageRequest.of(page, size, sortObj);
        if (search != null) {
            search = search.toLowerCase().trim();
        }

        Page<ProductEntity> result;

        if (search == null || search.isBlank()) {

            if (statusEnum == null) {
                result = repository.findAll(pageable);
            } else {
                result = repository.findAllByStatus(statusEnum, pageable);
            }

        } else {
            result = repository.search(statusEnum, search, pageable);
        }

        return result.map(mapper::toDTO);
    }

    public ProductDTO update(UUID id, UpdateProductRequest request) {

        ProductEntity product = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        if (request.getStatus() != null) {
            product.setStatus(ProductEntity.Status.valueOf(request.getStatus()));
        }

        return mapper.toDTO(repository.save(product));
    }

    public void delete(UUID id) {
        ProductEntity product = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Product not found"));

        repository.delete(product);
    }

    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ProductDTO findById(UUID id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return mapper.toDTO(entity);
    }
}