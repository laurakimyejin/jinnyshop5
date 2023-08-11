package com.jinnyshop5.productImg.repository;


import com.jinnyshop5.productImg.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductOrderByIdAsc(Long productId);

    ProductImg findByIdAndRepimgYn(Long productId, String repimgYn);

    List<ProductImg> findByIdOrderByIdAsc(Long itemId);
}