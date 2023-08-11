package com.jinnyshop5.common.controller;

import com.jinnyshop5.product.dto.MainProductDto;
import com.jinnyshop5.product.dto.ProductSearchDto;
import com.jinnyshop5.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping(value = "/")
    public String main(ProductSearchDto productSearchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
//        Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

//        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}
