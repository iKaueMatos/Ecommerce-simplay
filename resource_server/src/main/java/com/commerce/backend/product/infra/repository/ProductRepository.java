package com.commerce.backend.product.infra.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.commerce.backend.product.infra.entity.Product;
import com.commerce.backend.product.infra.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    Optional<Product> findByUrl(String url);

    List<Product> findAllByProductCategory(Pageable pageable, ProductCategory productCategory);

    List<Product> findTop8ByOrderByDateCreatedDesc();

    List<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findTop8ByProductCategoryAndIdIsNot(ProductCategory productCategory, Long id);

    List<Product> findAllByProductCategoryIsNot(ProductCategory productCategory, Pageable pageable);
}
