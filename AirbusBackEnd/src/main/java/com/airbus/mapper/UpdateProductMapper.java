package com.airbus.mapper;

import com.airbus.dto.ProductDTO;
import com.airbus.dto.UpdateProductDTO;
import com.airbus.pojo.Product;
import org.mapstruct.Mapper;

@Mapper
public interface UpdateProductMapper {
    UpdateProductDTO productPojoToUpdateProductDto(Product product);
    Product updateProductDtoToProductPojo(UpdateProductDTO updateProductDTO);
}
