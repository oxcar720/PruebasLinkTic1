package com.oxcar720.products_service.repository;

import com.oxcar720.products_service.entity.ProductEntity;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findBySku(String sku);

    Page<ProductEntity> findAllByStatus(
        ProductEntity.Status status,
        Pageable pageable
    );

    @Query("""
        SELECT p FROM ProductEntity p
        WHERE (:status IS NULL OR p.status = :status)
        AND (
            LOWER(p.name) LIKE CONCAT('%', :search, '%') OR
            LOWER(p.sku) LIKE CONCAT('%', :search, '%')
        )
    """)
    Page<ProductEntity> search(
            @Param("status") ProductEntity.Status status,
            @Param("search") String search,
            Pageable pageable
    );
}