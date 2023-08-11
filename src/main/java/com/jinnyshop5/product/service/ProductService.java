package com.jinnyshop5.product.service;

import com.jinnyshop5.product.dto.MainProductDto;
import com.jinnyshop5.product.dto.ProductFormDto;
import com.jinnyshop5.productImg.dto.ProductImgDto;
import com.jinnyshop5.product.dto.ProductSearchDto;
import com.jinnyshop5.product.entity.Product;
import com.jinnyshop5.productImg.entity.ProductImg;
import com.jinnyshop5.productImg.repository.ProductImgRepository;
import com.jinnyshop5.product.repository.ProductRepository;

import com.jinnyshop5.productImg.service.ProductImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImgService productImgService;
    private final ProductImgRepository productImgRepository;
    public Long saveItem(ProductFormDto productFormDto, List<MultipartFile> productImgFileList) throws Exception{

        //상품 등록
        Product product = productFormDto.createItem();
        productRepository.save(product);

        //이미지 등록
        for(int i=0;i<productImgFileList.size();i++){
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);

            if(i == 0)
                productImg.setRepimgYn("Y");
            else
                productImg.setRepimgYn("N");

            productImgService.saveProductImg(productImg, productImgFileList.get(i));
        }

        return product.getId();
    }

    public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> productImgFileList) throws Exception{

        //상품 수정
        Product product = productRepository.findById(productFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        product.updateProduct(productFormDto);

        List<Long> productImgIds = productFormDto.getProductImgIds();

        //이미지 등록
        for(int i=0;i<productImgFileList.size();i++){
            productImgService.updateItemImg(productImgIds.get(i), productImgFileList.get(i));
        }
        return product.getId();
    }

    @Transactional(readOnly = true)
    public ProductFormDto getItemDtl(Long itemId){

        List<ProductImg> itemImgList = productImgRepository.findByIdOrderByIdAsc(itemId);
        List<ProductImgDto> itemImgDtoList = new ArrayList<>();
        for (ProductImg itemImg : itemImgList) {
            ProductImgDto itemImgDto = ProductImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        Product product = productRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto itemFormDto = ProductFormDto.of(product);
        itemFormDto.setProductImgDtoList(itemImgDtoList);
        return itemFormDto;
    }
    /*
        @Transactional(readOnly = true)
        public Page<product> getAdminItemPage(ProductSearchDto itemSearchDto, Pageable pageable){
            return ProductRepository.getAdminProductPage(itemSearchDto, pageable);
        }
    */
//    @Transactional(readOnly = true)
//    public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable){
//        return productRepository.getMainProductPage(productSearchDto, pageable);
//    }

    public ProductFormDto getProductDetail(Long productId) {
        return getProductDetail(productId);
    }
}