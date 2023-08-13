package com.jinnyshop5.Cart.model;

import com.jinnyshop5.Member.model.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="Cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name="cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public static Cart createCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
