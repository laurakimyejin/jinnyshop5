package com.jinnyshop5.Main.controller;

import com.jinnyshop5.Product.dto.MainProductDto;
import com.jinnyshop5.Product.dto.ProductSearchDto;
import com.jinnyshop5.Product.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ProductService productService;

    @GetMapping(value = "/")
    public String main(ProductSearchDto productSearchDto, Optional<Integer> Page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}
