package com.jinnyshop5.cart.controller;

import com.jinnyshop5.cart.dto.CartDetailDto;
import com.jinnyshop5.cart.service.CartService;
import com.jinnyshop5.cartProduct.dto.CartOrderDto;
import com.jinnyshop5.cartProduct.dto.CartProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody
    ResponseEntity order(@RequestBody @Valid CartProductDto cartProductDto, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartProductId;

        try {
            cartProductId = cartService.addCart(cartProductDto, email);
        } catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model){
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        log.info(principal.getName());
        model.addAttribute("cartProducts", cartDetailList);
        return "cart/cartList";
    }

    @PatchMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductId, int count, Principal principal){

        if(count <= 0){
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartProduct(cartProductId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartProductCount(cartProductId, count);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId, Principal principal){

        if(!cartService.validateCartProduct(cartProductId, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartProduct(cartProductId);
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity cartOrder(@RequestBody CartOrderDto cartOrderDto, Principal principal){

        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDto cartOrder : cartOrderDtoList) {
            if(!cartService.validateCartProduct(cartOrder.getCartProductId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.cartOrder(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}