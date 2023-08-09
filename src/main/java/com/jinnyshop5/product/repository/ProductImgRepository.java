package com.jinnyshop5.product.repository;

import com.jinnyshop5.product.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductOrderByIdAsc(Long productId);

    ProductImg findByItemIdAndRepimgYn(Long productId, String repimgYn);

    List<ProductImg> findByItemIdOrderByIdAsc(Long itemId);
}
