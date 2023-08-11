package com.jinnyshop5.product.controller;

import com.jinnyshop5.product.dto.ProductFormDto;
import com.jinnyshop5.product.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@NoArgsConstructor
public class ProductController {



    private ProductService service;

    @GetMapping(value = "/admin/prodcut/new")
    public String itemForm(Model model){
        model.addAttribute("productFormDto", new ProductFormDto());
        return "product/productForm";
    }

    @PostMapping(value = "/admin/prodcut/new")
    public String itemNew(@Valid ProductFormDto productFormDto, BindingResult bindingResult, Model model, @RequestParam("productImgFile") List<MultipartFile> productImgFileList){

        if(bindingResult.hasErrors()){
            return "product/productForm";
        }

        if(productImgFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "product/productForm";
        }

        try {
            service.saveItem(productFormDto, productImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "product/productForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/product/{productId}")
    public String productDtl(@PathVariable("productId") Long itemId, Model model){

        try {
            ProductFormDto itemFormDto = service.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("productFormDto", new ProductFormDto());
            return "product/productForm";
        }

        return "product/productForm";
    }

    @PostMapping(value = "/admin/product/{productId}")
    public String productUpdate(@Valid ProductFormDto productFormDto, BindingResult bindingResult, @RequestParam("productImgFile") List<MultipartFile> productImgFileList,
                                Model model){

        if(bindingResult.hasErrors()){
            return "product/productForm";
        }

        if(productImgFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "product/productForm";
        }

        try {
            service.updateProduct(productFormDto, productImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "product/productForm";
        }

        return "redirect:/";
    }
/*
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ProductSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<product> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "item/itemMng";
    }
*/
    @GetMapping(value = "/item/{itemId}")
    public String productDtl(Model model, @PathVariable("productId") Long productId){
        ProductFormDto itemFormDto = service.getProductDetail(productId);
        model.addAttribute("item", itemFormDto);
        return "item/itemDtl";
    }

}