package com.airbus.serviceImpl;

import com.airbus.dto.ProductDTO;
import com.airbus.dto.UpdateProductDTO;
import com.airbus.exception.NoProductsForCategory;
import com.airbus.exception.ProductDTOValidationFailure;
import com.airbus.mapper.ProductMapper;
import com.airbus.mapper.UpdateProductMapper;
import com.airbus.pojo.Product;
import com.airbus.repository.ProductRepository;
import com.airbus.service.AirBusService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.print.Pageable;
import java.util.*;

@Service
@Qualifier("airBusService")
public class AirBusServiceImpl implements AirBusService {
    @Autowired
    ProductRepository productRepository;

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    UpdateProductMapper updateProductMapper = Mappers.getMapper(UpdateProductMapper.class);
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Override
    public List<ProductDTO> getAllProducts(){
        Iterator<Product> productsIterator = productRepository.findAll().iterator();
        List<ProductDTO> productDTOS = new ArrayList<>();
        while (productsIterator.hasNext()){
            Product product = productsIterator.next();
            ProductDTO productDTO = productMapper.productPojoToProductDto(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductsByCategory(String category) throws NoProductsForCategory {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productRepository.findByCategory(category);
        if(products.isEmpty()){throw new NoProductsForCategory(category);}
        for(Product product: products){
            ProductDTO productDTO = productMapper.productPojoToProductDto(product);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductDTO createNewProduct(ProductDTO productDTO) throws ProductDTOValidationFailure {
        productDTO.setId(null);
        Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
        if(!violations.isEmpty()){
            Map<String, List<String>> validationErrors = new HashMap<>();
            for (ConstraintViolation<ProductDTO> violation : violations) {
                List<String> errors = validationErrors.getOrDefault(violation.getPropertyPath().toString(), new ArrayList<String>());
                errors.add(violation.getMessage());
                validationErrors.put(violation.getPropertyPath().toString(), errors);
            }
            throw new ProductDTOValidationFailure(validationErrors);
        }
        Product product = productMapper.productDtoToProductPojo(productDTO);
        Product outputProduct = productRepository.save(product);
        return productMapper.productPojoToProductDto(outputProduct);
    }

    @Override
    public ProductDTO updateExistingProduct(UpdateProductDTO updateProductDTO) throws ProductDTOValidationFailure {
        Set<ConstraintViolation<UpdateProductDTO>> violations = validator.validate(updateProductDTO);
        if(!violations.isEmpty()){
            Map<String, List<String>> validationErrors = new HashMap<>();
            for (ConstraintViolation<UpdateProductDTO> violation : violations) {
                List<String> errors = validationErrors.getOrDefault(violation.getPropertyPath().toString(), new ArrayList<String>());
                errors.add(violation.getMessage());
                validationErrors.put(violation.getPropertyPath().toString(), errors);
            }
            throw new ProductDTOValidationFailure(validationErrors);
        }
        Product product = updateProductMapper.updateProductDtoToProductPojo(updateProductDTO);
        Product outputProduct = productRepository.save(product);
        return productMapper.productPojoToProductDto(outputProduct);
    }


    // Tried for lazy loading of page
    @Override
    public long countAllProducts(){
        return productRepository.count();
    }

    @Override
    public List<ProductDTO> getAllProductsByPage(int pageNo, int pageSize){
        Iterator<Product> productsIterator = productRepository.findAll(PageRequest.of(pageNo,pageSize)).iterator();
        List<ProductDTO> productDTOS = new ArrayList<>();
        while (productsIterator.hasNext()){
            Product product = productsIterator.next();
            ProductDTO productDTO = productMapper.productPojoToProductDto(product);
            productDTOS.add(productDTO);

        }
        return productDTOS;
    }

    @Override
    public List<ProductDTO> getAllProductsByPageAndSort(int pageNo, int pageSize, String[] sortArray){
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Sort> sortList = new ArrayList<>();
        for(int i=0; i<sortArray.length-1; i++){
            if(sortArray[i+1].equals("asc")){
                Sort sort = Sort.by(sortArray[i]).ascending();
                sortList.add(sort);
            } else if (sortArray[i+1].equals("desc")){
                Sort sort = Sort.by(sortArray[i]).descending();
                sortList.add(sort);
            }
        }
        Optional<Sort> optionalSort = sortList.stream().sequential().reduce((sort1, sort2) -> {return sort1.and(sort2);});
        if(optionalSort.isPresent()){
            Iterator<Product> productsIterator = productRepository.findAll(PageRequest.of(pageNo,pageSize,optionalSort.get())).iterator();
            while (productsIterator.hasNext()){
                Product product = productsIterator.next();
                ProductDTO productDTO = productMapper.productPojoToProductDto(product);
                productDTOS.add(productDTO);
            }
        }
        return productDTOS;
    }

}
