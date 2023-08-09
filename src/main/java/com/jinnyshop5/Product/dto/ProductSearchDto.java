package com.jinnyshop5.Product.dto;

import com.jinnyshop5.Product.constant.ProductStatus;
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