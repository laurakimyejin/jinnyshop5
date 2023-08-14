package com.jinnyshop5.order.model;

import com.jinnyshop5.common.model.BaseTimeEntity;
import com.jinnyshop5.member.model.Member;
import com.jinnyshop5.order.constant.OrderStatus;
import com.jinnyshop5.orderProduct.model.OrderProduct;
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
public class Order extends BaseTimeEntity {

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
    //cascade-주문상품선택->order엔티티저장->order product엔티티저장
    //orphanRemoval-oder에서 order product 삭제 했을때, order produuct 함께 삭제
    private List<OrderProduct> orderProducts = new ArrayList<>();//하나의 주문-여러주문상품이라서 리스트

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderProduct> orderProductList){
        Order order = new Order();
        order.setMember(member);
        for(OrderProduct orderProduct : orderProductList){
            order.addOrderProduct(orderProduct);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderProduct orderProduct : orderProducts){
            totalPrice += orderProduct.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderProduct orderProduct : orderProducts){
            orderProduct.cancel();
        }
    }
}