package com.oxcar720.inventory_service.repository;

import com.oxcar720.inventory_service.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryEntity i WHERE i.id = :productId")
    Optional<InventoryEntity> findForUpdate(@Param("productId") UUID productId);
}