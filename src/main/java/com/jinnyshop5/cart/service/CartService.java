package com.jinnyshop5.cart.service;

import com.jinnyshop5.cart.dto.CartDetailDto;
import com.jinnyshop5.cart.model.Cart;
import com.jinnyshop5.cart.repository.CartRepository;
import com.jinnyshop5.cartProduct.dto.CartOrderDto;
import com.jinnyshop5.cartProduct.dto.CartProductDto;
import com.jinnyshop5.cartProduct.model.CartProduct;
import com.jinnyshop5.cartProduct.repository.CartProductRepository;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.member.repository.MemberRepository;
import com.jinnyshop5.order.dto.OrderDto;
import com.jinnyshop5.order.service.OrderService;
import com.jinnyshop5.product.entity.Product;
import com.jinnyshop5.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final CartRepository cartRepository;

    private final CartProductRepository cartProductRepository;

    private final OrderService orderService;

    public Long addCart(CartProductDto cartProductDto, String email){
        Product product = productRepository.findById(cartProductDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected product_id"));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));;

        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartProduct savedCartProduct = cartProductRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if(savedCartProduct != null){
            savedCartProduct.addCount(cartProductDto.getCount());
            return savedCartProduct.getId();
        }else {
            CartProduct cartProduct = CartProduct.createCartProduct(cart, product, cartProductDto.getCount());
            cartProductRepository.save(cartProduct);
            return cartProduct.getId();
        }
    }

    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));

        log.info(member.getMemberName());

        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartProductRepository.findByCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }


    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email){
        Member curmember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        Member savedMember = cartProduct.getCart().getMember();

        if(!StringUtils.equals(curmember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void deleteCartProduct(Long cartProductId){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);
    }

    public Long cartOrder(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartProduct = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);
            OrderDto orderDto = OrderDto.createOderDto(cartProduct.getProduct().getId(), cartProduct.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, email);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartProduct cartItem = cartProductRepository.findById(cartOrderDto.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);
            cartProductRepository.delete(cartItem);
        }

        return orderId;
    }

    public void updateCartProductCount(Long cartProductId, int count){
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);

        cartProduct.updateCount(count);
    }

}