package com.airbus.mapper;

import com.airbus.dto.ProductDTO;
import com.airbus.pojo.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    ProductDTO productPojoToProductDto(Product source);
    Product productDtoToProductPojo(ProductDTO destination);
}
