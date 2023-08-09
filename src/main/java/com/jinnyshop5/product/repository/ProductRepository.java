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

    /*상품명으로 검색*/
    List<Product> findByProductName(String productName);

    /*상품명, 상세설명 OR*/
    List<Product> findByProductNameOrProductDetail(String productName, String productDetail);

    /*가격이 적은*/
    List<Product> findByPriceLessThan(Integer price);

    /*Sort*/
    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

    /*상품설명 검색*/
    @Query("SELECT p from Product p where p.productDetail like %:productDetail% order by p.price desc")
    List<Product> findByProductDetail(@Param("productDetail") String productDetail);

    Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);

//    @Query(value = "SELECT p from Product p where p.productDetail like %:productDetail% order by p.price desc",
//            nativeQuery = true)
//    List<Product> findByProductDetail(@Param("productDetail") String productDetail);
}
