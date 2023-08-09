package com.jinnyshop5.Order.model;

import com.jinnyshop5.Member.model.Member;
import com.jinnyshop5.Order.constant.OrderStatus;
import com.jinnyshop5.OrderProduct.model.OrderProduct;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    /*한명의 회원이 여러 주문 할 수 있음*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    //주문상품 1:n
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();//하나의 주문-여러주문상품이라서 리스트

    private LocalDateTime regTime;

    private LocalDateTime updateTime;



}
