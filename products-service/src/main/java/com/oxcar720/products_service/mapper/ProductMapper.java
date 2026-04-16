package com.oxcar720.products_service.mapper;

import com.oxcar720.products_service.dto.*;
import com.oxcar720.products_service.entity.ProductEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(ProductEntity entity);

    ProductEntity toEntity(CreateProductRequest request);
}