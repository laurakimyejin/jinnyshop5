package com.jinnyshop5.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {
    private Long id;
    private String productName;
    private String productDetail;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainProductDto(Long d, String productName, String productDetaile, String imgUrl, Integer price){
        this.id = id;
        this.productName = productName;
        this.productDetail = productDetaile;
        this.imgUrl = imgUrl;
        this.price = price;

    }
}