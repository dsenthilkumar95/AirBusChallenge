package com.airbus.service;

import com.airbus.dto.ProductDTO;
import com.airbus.dto.UpdateProductDTO;
import com.airbus.exception.NoProductsForCategory;
import com.airbus.exception.ProductDTOValidationFailure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AirBusService {
    public List<ProductDTO>  getAllProducts();
    public List<ProductDTO> getAllProductsByCategory(String category) throws NoProductsForCategory;
    public ProductDTO createNewProduct(ProductDTO productDTO) throws ProductDTOValidationFailure;
    public ProductDTO updateExistingProduct(UpdateProductDTO updateProductDTO) throws ProductDTOValidationFailure;

    // Tried for lazy loading of page
    public long countAllProducts();
    public List<ProductDTO> getAllProductsByPage(int pageNo, int pageSize);
    public List<ProductDTO> getAllProductsByPageAndSort(int pageNo, int pageSize, String[] sortArray);
}
