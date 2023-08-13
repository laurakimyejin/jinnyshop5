package com.jinnyshop5.Product.repository;

import com.jinnyshop5.Product.dto.MainProductDto;
import com.jinnyshop5.Product.dto.ProductSearchDto;
import com.jinnyshop5.Product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,
        QuerydslPredicateExecutor<Product> {
    List<Product> findByProductName(String productName);

    static Product findProductId(long id) {
        return findProductId(id);
    }

    List<Product> findByProductNameOrProductDetail(String productName, String ProductDetail);

    List<Product> findByPriceLessThan(Integer price);

    List<Product> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Product i where i.productDetail like %:productDetail% order by i.price desc")
    List<Product> findByProductDetail(@Param("productDetail") String productDetail);

    @Query(value="select * from product i where i.product_detail like %:productDetail% order by i.price desc", nativeQuery = true)
    List<Product> findByProductDetailByNative(@Param("productDetail") String productDetail);

    Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);
}
