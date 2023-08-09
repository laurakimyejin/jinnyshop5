package com.jinnyshop5.Product.model;

import com.jinnyshop5.Product.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Product")
@Getter
@Setter
@ToString
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//상품코드

    @Column(nullable = false, name="product_name")
    private String productName;//상품명

    @Lob
    @Column(nullable = false)
    private String productDetail;//상품상세설명


    @Column(nullable = false, name="price")
    private int price;//상품가격

    @Column(nullable = false, name="sales")
    private int sales;//판매수량

    @Column(nullable = false)
    private int stock;//재고수량

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


}
