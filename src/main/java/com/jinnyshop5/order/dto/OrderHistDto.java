package com.jinnyshop5.order.dto;
import com.jinnyshop5.order.constant.OrderStatus;
import com.jinnyshop5.order.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderHistDto {
    private Long orderId;
    private final String orderDate;
    private OrderStatus orderStatus;
    private List<OrderProductDto> orderProductDtoList = new ArrayList<>();

    public OrderHistDto(Order order){
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"));
    }

    public void addOrderProductDto(OrderProductDto orderProductDto){
        orderProductDtoList.add(orderProductDto);
    }
}