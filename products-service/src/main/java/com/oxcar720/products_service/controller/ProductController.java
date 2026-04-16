package com.oxcar720.products_service.controller;

import com.oxcar720.products_service.dto.*;
import com.oxcar720.products_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ProductDTO create(@Valid @RequestBody CreateProductRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ProductDTO update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequest request
    ) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping
    public Page<ProductDTO> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        return service.search(status, search, page, size, sort);
    }
}