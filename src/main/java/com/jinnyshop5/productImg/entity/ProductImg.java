package com.jinnyshop5.productImg.entity;

import com.jinnyshop5.common.model.BaseEntity;
import com.jinnyshop5.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "product_img")
public class ProductImg extends BaseEntity {

    @Id
    @Column(name="product_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;  //이미지파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;  //이미지 조회 경로

    private String repimgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void updateProductImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}