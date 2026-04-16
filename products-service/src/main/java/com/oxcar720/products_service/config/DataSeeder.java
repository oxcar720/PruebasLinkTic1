package com.oxcar720.products_service.config;

import com.oxcar720.products_service.entity.ProductEntity;
import com.oxcar720.products_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        List<ProductEntity> products = List.of(
                ProductEntity.builder()
                        .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .sku("SKU-1")
                        .name("Laptop")
                        .price(new BigDecimal("1200"))
                        .build(),
                ProductEntity.builder()
                        .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                        .sku("SKU-2")
                        .name("Mouse")
                        .price(new BigDecimal("25"))
                        .build(),
                ProductEntity.builder()
                        .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                        .sku("SKU-3")
                        .name("Keyboard")
                        .price(new BigDecimal("50"))
                        .build(),
                ProductEntity.builder()
                        .id(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                        .sku("SKU-4")
                        .name("Monitor")
                        .price(new BigDecimal("800"))
                        .build(),
                ProductEntity.builder()
                        .id(UUID.fromString("55555555-5555-5555-5555-555555555555"))
                        .sku("SKU-5")
                        .name("Cargador")
                        .price(new BigDecimal("20"))
                        .build()
        );

        repository.saveAll(products);
    }
}