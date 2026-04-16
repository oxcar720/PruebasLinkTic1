package com.oxcar720.inventory_service.config;

import com.oxcar720.inventory_service.entity.InventoryEntity;
import com.oxcar720.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final InventoryRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        List<InventoryEntity> inventoryList = List.of(
                InventoryEntity.builder()
                        .productId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .available(10)
                        .reserved(0)
                        .build(),
                InventoryEntity.builder()
                        .productId(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                        .available(15)
                        .reserved(0)
                        .build(),
                InventoryEntity.builder()
                        .productId(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                        .available(20)
                        .reserved(0)
                        .build(),
                InventoryEntity.builder()
                        .productId(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                        .available(5)
                        .reserved(0)
                        .build(),
                InventoryEntity.builder()
                        .productId(UUID.fromString("55555555-5555-5555-5555-555555555555"))
                        .available(30)
                        .reserved(0)
                        .build()
        );

        repository.saveAll(inventoryList);
    }
}