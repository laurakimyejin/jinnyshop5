package com.jinnyshop5.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartDetailDto {

    private Long cartProductId;

    private String productName;

    private int price;

    private int count;

    private String imgUrl;

    public CartDetailDto(Long cartProductId, String productName, int price, int count, String imgUrl){
        this.cartProductId = cartProductId;
        this.productName = productName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.count = count;

    }
}