package com.jinnyshop5.Cart.service;

import com.jinnyshop5.Cart.dto.CartDetailDto;
import com.jinnyshop5.Cart.model.Cart;
import com.jinnyshop5.Cart.repository.CartRepository;
import com.jinnyshop5.CartProduct.dto.CartOrderDto;
import com.jinnyshop5.CartProduct.dto.CartProductDto;
import com.jinnyshop5.CartProduct.model.CartProduct;
import com.jinnyshop5.CartProduct.repository.CartProductRepository;
import com.jinnyshop5.Member.model.Member;
import com.jinnyshop5.Member.repository.MemberRepository;
import com.jinnyshop5.Order.dto.OrderDto;
import com.jinnyshop5.Order.service.OrderService;
import com.jinnyshop5.Product.entity.Product;
import com.jinnyshop5.Product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
        Product product = ProductRepository.findProductId(cartProductDto.getProductId());

        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartProduct savedCartProduct = cartProductRepository.findCartIdAndProductId(cart.getId(), product.getId());

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

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartProductRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }


    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String email){
        Member curmember = memberRepository.findByEmail(email);
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