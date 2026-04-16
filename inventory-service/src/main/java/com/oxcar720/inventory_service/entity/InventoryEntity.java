package com.oxcar720.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {

    @Id
    private UUID productId;

    private Integer available;

    private Integer reserved;

    @Version
    private Integer version;
}