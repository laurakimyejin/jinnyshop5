package com.jinnyshop5.product.service;

import com.jinnyshop5.product.entity.ProductImg;
import com.jinnyshop5.product.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImgService {

    @Value("${productImgLocation}")
    private String productImgLocation;

    private final ProductImgRepository productImgRepository;
    private final FileService fileService;


    public void saveProductImg(ProductImg productImg, MultipartFile productImgFile) throws Exception{
        String oriImgName = productImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;
        }

        //상품 이미지 정보 저장
        productImg.updateProductImg(oriImgName, imgName, imgUrl);
        productImgRepository.save(productImg);
    }

    public void updateItemImg(Long productImgId, MultipartFile productImgFile/*, ProductImg savedproductImg*/) throws Exception{
        if(!productImgFile.isEmpty()){
          ProductImg savedProductImg = productImgRepository.findById(productImgId).orElseThrow(EntityNotFoundException :: new);
          String imgUrl = savedProductImg.getImgUrl();

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedProductImg.getImgName())) {
                fileService.deleteFile(imgUrl);
            }

            String oriImgName = productImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(productImgLocation, oriImgName, productImgFile.getBytes());
            imgUrl = "/images/item/" + imgName;

            /*savedproductImg*/savedProductImg.updateProductImg(oriImgName, imgName, imgUrl);
        }
    }


}