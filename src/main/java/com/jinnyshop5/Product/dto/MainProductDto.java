package com.jinnyshop5.Product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {
    private Long id;
    private String productNm;
    private String productDetail;
    private String imgUrl;
    private Integer price;

    @QueryProjection
    public MainProductDto(Long id, String productNm, String itemDetail, String imgUrl, Integer price){
        this.id = id;
        this.productNm = productNm;
        this.productDetail = productNm;
        this.imgUrl = imgUrl;
        this.price = price;

    }
}