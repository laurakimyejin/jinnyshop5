package com.jinnyshop5.CartProduct.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartProductId;

    List<CartOrderDto> cartOrderDtoList;
}