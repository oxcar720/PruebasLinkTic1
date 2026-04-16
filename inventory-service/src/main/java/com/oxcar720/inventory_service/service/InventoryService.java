package com.oxcar720.inventory_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxcar720.inventory_service.dto.InventoryDTO;
import com.oxcar720.inventory_service.dto.PurchaseRequest;
import com.oxcar720.inventory_service.dto.UpdateInventoryRequest;
import com.oxcar720.inventory_service.entity.IdempotencyEntity;
import com.oxcar720.inventory_service.entity.InventoryEntity;
import com.oxcar720.inventory_service.exception.NotFoundException;
import com.oxcar720.inventory_service.mapper.InventoryMapper;
import com.oxcar720.inventory_service.repository.IdempotencyRepository;
import com.oxcar720.inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oxcar720.inventory_service.client.ProductClient;

import java.time.LocalDateTime;
import org.springframework.data.domain.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;
    private final ProductClient productClient;
    private final IdempotencyRepository idempotencyRepository;
    private final ObjectMapper objectMapper;

    public InventoryDTO getByProduct(java.util.UUID productId) {
        InventoryEntity entity = repository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        return mapper.toDTO(entity);
    }

    public Page<InventoryDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional
    public InventoryDTO update(UUID id, UpdateInventoryRequest request) {

        InventoryEntity entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        entity.setAvailable(request.getAvailable());

        return mapper.toDTO(repository.save(entity));
    }

    @Transactional
    public String syncProducts() {

        List<Map<String,Object>> products = productClient.findAllProducts();

        Set<UUID> productIds = new HashSet<>();

        int created = 0;
        int deleted = 0;

        for (Map<String,Object> item : products) {

            UUID id = UUID.fromString(
                item.get("id").toString()
            );

            productIds.add(id);

            if (!repository.existsById(id)) {

                repository.save(
                    InventoryEntity.builder()
                        .productId(id)
                        .available(0)
                        .reserved(0)
                        .build()
                );

                created++;
            }
        }
        List<InventoryEntity> allInventory = repository.findAll();

        for (InventoryEntity inv : allInventory) {

            if (!productIds.contains(inv.getProductId())) {
                repository.delete(inv);
                deleted++;
            }
        }
        return "Sincronización completada. Nuevos: "
                + created +
                " | Eliminados: " +
                deleted;
    }

    @Transactional
    public InventoryDTO purchase(String key, PurchaseRequest request) {

        var existing = idempotencyRepository.findById(key);
        if (existing.isPresent()) {
            try {
                return objectMapper.readValue(existing.get().getResponse(), InventoryDTO.class);
            } catch (Exception e) {
                throw new RuntimeException("Error reading cached response");
            }
        }

        if (!productClient.exists(request.getProductId())) {
            throw new NotFoundException("Product not found");
        }

        InventoryEntity inventory = repository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Inventory not found"));

        if (inventory.getAvailable() < request.getQuantity()) {
            throw new RuntimeException("Not enough stock");
        }

        inventory.setAvailable(inventory.getAvailable() - request.getQuantity());
        inventory.setReserved(inventory.getReserved() + request.getQuantity());

        InventoryEntity updated = repository.save(inventory);
        InventoryDTO response = mapper.toDTO(updated);

        try {
            idempotencyRepository.save(
                    IdempotencyEntity.builder()
                            .idempotencyKey(key)
                            .response(objectMapper.writeValueAsString(response))
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            log.error("Error saving idempotency", e);
        }

        log.info("InventoryChanged productId={} available={} reserved={}",
                updated.getProductId(),
                updated.getAvailable(),
                updated.getReserved());

        return response;
    }
}