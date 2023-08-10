package com.jinnyshop5.ProductImg.repository;
import com.jinnyshop5.Product.dto.MainProductDto;
import com.jinnyshop5.Product.dto.ProductSearchDto;
import com.jinnyshop5.Product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminItemPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page<MainProductDto> getMainItemPage(ProductSearchDto productSearchDto, Pageable pageable);

}