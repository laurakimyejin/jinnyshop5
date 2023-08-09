package com.jinnyshop5.product.entity;

import com.jinnyshop5.product.constant.ProductStatus;
import com.jinnyshop5.product.dto.ProductFormDto;
import com.jinnyshop5.product.exception.OutOfStockException;
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

    @Column(nullable= false, name="image")
    private String image;//상품이미지

    @Column(nullable = false, name="price")
    private int price;//상품가격

    @Column(nullable = false, name="sales")
    private int sales;//판매수량

    @Column(nullable = false)
    private int stockNumber;//재고수량

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;


    private LocalDateTime regTime;

    private LocalDateTime updateTime;


    public void updateProduct(ProductFormDto productFormDto) {
        this.productName = productFormDto.getProductNm();
        this.price = productFormDto.getPrice();
        this.stockNumber = productFormDto.getStockNumber();
        this.productDetail = productFormDto.getProductDetail();
        this.productStatus = productFormDto.getProductStatus();
    }

    public void removeStock(int stockNumber){
        int restStock = this.stockNumber - stockNumber;
        if(restStock<0){
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

}
