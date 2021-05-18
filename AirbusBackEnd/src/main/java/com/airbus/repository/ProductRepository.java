package com.airbus.repository;

import com.airbus.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
    public List<Product> findByCategory(String Category);
    public Page<Product> findAll(Pageable pageable);
    
}
