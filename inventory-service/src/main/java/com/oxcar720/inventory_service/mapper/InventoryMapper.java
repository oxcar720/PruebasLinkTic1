package com.oxcar720.inventory_service.mapper;

import com.oxcar720.inventory_service.dto.InventoryDTO;
import com.oxcar720.inventory_service.entity.InventoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryDTO toDTO(InventoryEntity entity);
}