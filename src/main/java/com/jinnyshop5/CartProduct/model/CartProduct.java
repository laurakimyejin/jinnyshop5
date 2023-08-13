package com.jinnyshop5.CartProduct.model;

import com.jinnyshop5.Cart.model.Cart;
import com.jinnyshop5.Product.entity.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Cart_product")
public class CartProduct {

    @Id
    @GeneratedValue
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;

    //장바구니에 담을 상품 정보 알아야하므로 매핑. 하나의 상품이 여러 장바구니의 장바구니 상품으로 담길수 있음 .다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    //같은 상품을 카트에 몇개 담을지?
    private int count;

    public static CartProduct createCartProduct(Cart cart, Product product, int count){
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setCount(count);
        return cartProduct;
    }



    public void addCount(int count){
        this.count += count;
    }
    public void updateCount(int count){
        this.count = count;
    }
}
