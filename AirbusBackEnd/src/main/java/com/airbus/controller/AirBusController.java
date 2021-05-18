package com.airbus.controller;

import com.airbus.dto.ProductDTO;
import com.airbus.dto.UpdateProductDTO;
import com.airbus.exception.NoProductsForCategory;
import com.airbus.exception.ProductDTOValidationFailure;
import com.airbus.service.AirBusService;
import com.airbus.serviceImpl.AirBusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AirBusController {

    @Autowired
    @Qualifier("airBusService")
    AirBusService airBusService;

    @GetMapping(path = "/getAllProducts", produces = "application/json", consumes = "application/json")
    public List<ProductDTO> getAllProducts(){
        return airBusService.getAllProducts();
    }

    @GetMapping(path = "/getAllProductsByCategory/{category}", produces = "application/json", consumes = "application/json")
    public List<ProductDTO> getAllProductsByCategory(@PathVariable String category) throws NoProductsForCategory {
        return airBusService.getAllProductsByCategory(category);
    }

    @PostMapping(path = "/createNewProduct", produces = "application/json", consumes = "application/json")
    public ProductDTO createNewProduct(@RequestBody ProductDTO productDTO) throws ProductDTOValidationFailure {
        return airBusService.createNewProduct(productDTO);
    }

    @PutMapping(path = "/updateExistingProduct", produces = "application/json", consumes = "application/json")
    public ProductDTO updateExistingProduct(@RequestBody UpdateProductDTO updateProductDTO) throws Exception{
        return airBusService.updateExistingProduct(updateProductDTO);
    }

    // Tried for lazy loading of page
    @GetMapping(path = "/countAllProducts", produces = "application/json", consumes = "application/json")
    public long countAllProducts(){
        return airBusService.countAllProducts();
    }

    @GetMapping(path = "/getAllProductsByPage", produces = "application/json", consumes = "application/json")
    public List<ProductDTO> getAllProductsByPage(@RequestParam int pageNo, @RequestParam int pageSize){
        return airBusService.getAllProductsByPage(pageNo, pageSize);
    }

    @GetMapping(path = "/getAllProductsByPageAndSort", produces = "application/json", consumes = "application/json")
    public List<ProductDTO> getAllProductsByPageAndSort(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String sort){
        String[] sortParams = sort.split(",");
        return airBusService.getAllProductsByPageAndSort(pageNo, pageSize, sortParams);
    }
}
