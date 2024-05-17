package com.commerce.backend.product.infra.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.product.infra.entity.ProductCategory;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
    List<ProductCategory> findAllByOrderByName();
}
