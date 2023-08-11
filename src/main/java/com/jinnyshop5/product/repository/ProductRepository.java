package com.jinnyshop5.product.repository;

import com.jinnyshop5.product.dto.MainProductDto;
import com.jinnyshop5.product.dto.ProductSearchDto;
import com.jinnyshop5.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product> {
    List<Product> findByProductName(String productName);

    List<Product> findByProductNameOrProductDetail(String productName, String productDetail);

    List<Product> findByPriceLessThan(Integer price);

    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Product i where i.productDetail like %:findProductDetail% order by i.price desc")
    List<Product> findByProductDetail(@Param("findProductDetail") String findProductDetail);

//    Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);
}
