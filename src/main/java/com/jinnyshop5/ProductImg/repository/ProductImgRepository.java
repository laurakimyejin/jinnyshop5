package com.jinnyshop5.ProductImg.repository;

import com.jinnyshop5.ProductImg.model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findByProductOrderByIdAsc(Long productId);

    ProductImg findByItemIdAndRepimgYn(Long productId, String repimgYn);

    List<ProductImg> findByItemIdOrderByIdAsc(Long itemId);
}
