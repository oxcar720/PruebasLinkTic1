package com.oxcar720.inventory_service.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {

    private UUID productId;
    private Integer available;
    private Integer reserved;
}