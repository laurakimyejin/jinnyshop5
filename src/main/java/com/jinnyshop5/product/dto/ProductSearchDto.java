package com.jinnyshop5.product.dto;

import com.jinnyshop5.product.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchDto {

    private String searchDateType;

    private ProductStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = " ";
}