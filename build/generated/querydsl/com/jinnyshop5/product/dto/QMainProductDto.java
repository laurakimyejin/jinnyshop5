package com.jinnyshop5.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.jinnyshop5.product.dto.QMainProductDto is a Querydsl Projection type for MainProductDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainProductDto extends ConstructorExpression<MainProductDto> {

    private static final long serialVersionUID = -125219277L;

    public QMainProductDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<String> productDetail, com.querydsl.core.types.Expression<String> imgUrl, com.querydsl.core.types.Expression<Integer> price) {
        super(MainProductDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, int.class}, id, productName, productDetail, imgUrl, price);
    }

}

