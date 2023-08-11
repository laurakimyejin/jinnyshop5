package com.jinnyshop5.productImg.repository;
import com.jinnyshop5.product.dto.MainProductDto;
import com.jinnyshop5.product.dto.ProductSearchDto;
import com.jinnyshop5.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminItemPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page<MainProductDto> getMainItemPage(ProductSearchDto productSearchDto, Pageable pageable);

}