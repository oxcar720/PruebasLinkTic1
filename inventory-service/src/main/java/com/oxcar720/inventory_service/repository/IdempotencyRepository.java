package com.oxcar720.inventory_service.repository;

import com.oxcar720.inventory_service.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyRepository extends JpaRepository<IdempotencyEntity, String> {
}