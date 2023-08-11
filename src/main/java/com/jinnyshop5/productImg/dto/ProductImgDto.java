package com.jinnyshop5.productImg.dto;


import com.jinnyshop5.productImg.entity.ProductImg;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ui.ModelMap;

@Getter
@Setter
public class ProductImgDto {
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMap modelMapper = new ModelMap();

    public static ProductImgDto of(ProductImg productImg){
        return modelMapper(productImg,ProductImg.class);
    }

    private static ProductImgDto modelMapper(ProductImg productImg, Class<ProductImg> productImgClass) {
        return modelMapper(productImg,ProductImg.class);
    }


}