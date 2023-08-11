package com.jinnyshop5.orderProduct.model;

import com.jinnyshop5.order.model.Order;
import com.jinnyshop5.product.entity.Product;

import com.jinnyshop5.common.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*orderproduct, order 엔티티 단방향 매핑*/
@Entity
@Getter
@Setter
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    //하나 상품->여러주문 상품 ..주문 상품 기준 다대일 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //한번주문 -> 여러 상품 주문.. 주문상품-주문 다대일 단방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문가격

    private int count; //수량.



}
