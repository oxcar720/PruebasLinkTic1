package com.oxcar720.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotency_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyEntity {

    @Id
    private String idempotencyKey;

    @Column(columnDefinition = "TEXT")
    private String response;

    private LocalDateTime createdAt;
}