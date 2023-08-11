package com.jinnyshop5.product.dto;

import com.jinnyshop5.product.entity.Product;
import com.jinnyshop5.productImg.dto.ProductImgDto;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import com.jinnyshop5.product.constant.ProductStatus;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFormDto {
    private Long id;

    @NotNull
    private String productName;

    @NotNull
    private Integer price;

    @NotNull
    private String productDetail;

    @NotNull
    private Integer stockNumber;

    private ProductStatus ProductStatus;

    private List<ProductImgDto> productImgDtoList = new ArrayList<>();

    private List<Long> productImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Product createItem() {
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDto of(Product product) {
        return modelMapper.map(product, ProductFormDto.class);
    }


}