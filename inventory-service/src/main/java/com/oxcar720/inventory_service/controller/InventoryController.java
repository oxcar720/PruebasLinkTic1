package com.oxcar720.inventory_service.controller;

import com.oxcar720.inventory_service.dto.InventoryDTO;
import com.oxcar720.inventory_service.dto.PurchaseRequest;
import com.oxcar720.inventory_service.dto.UpdateInventoryRequest;
import com.oxcar720.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{productId}")
    public InventoryDTO get(@PathVariable UUID productId) {
        return service.getByProduct(productId);
    }

    @GetMapping
    public Page<InventoryDTO> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.list(page, size);
    }

    @PutMapping("/{productId}")
    public InventoryDTO update(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateInventoryRequest request
    ) {
        return service.update(productId, request);
    }

    @PostMapping("/sync")
    public String sync() {
        return service.syncProducts();
    }

    @PostMapping("/purchase")
    public InventoryDTO purchase(
            @RequestHeader("Idempotency-Key") String key,
            @Valid @RequestBody PurchaseRequest request) {

        return service.purchase(key, request);
    }
}