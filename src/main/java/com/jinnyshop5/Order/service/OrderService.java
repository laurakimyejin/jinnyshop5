package com.jinnyshop5.Order.service;

import com.jinnyshop5.Member.model.Member;
import com.jinnyshop5.Member.repository.MemberRepository;
import com.jinnyshop5.Order.dto.OrderDto;
import com.jinnyshop5.Order.dto.OrderHistDto;
import com.jinnyshop5.Order.dto.OrderProductDto;
import com.jinnyshop5.Order.model.Order;
import com.jinnyshop5.Order.repository.OrderRepository;
import com.jinnyshop5.OrderProduct.model.OrderProduct;
import com.jinnyshop5.Product.entity.Product;
import com.jinnyshop5.Product.repository.ProductRepository;
import com.jinnyshop5.ProductImg.entity.ProductImg;
import com.jinnyshop5.ProductImg.repository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductImgRepository productImgRepository;
    private final OrderProductDto orderProductDto;

    public Long order(OrderDto orderDto, String email){
        Product product = ProductRepository.findProductId(orderDto.getProductId());
        Member member = memberRepository.findByEmail(email);

        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
        orderProductList.add(orderProduct);

        Order order = Order.createOrder(member, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable){
        List<Order> orders = orderRepository.findOrders(email, pageable);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders){
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderProduct> orderProducts = order.getOrderProducts();
            for (OrderProduct orderProduct : orderProducts){
                ProductImg productImg = productImgRepository.findByItemIdAndRepimgYn(orderProduct.getProduct().getId(), "Y");
                orderHistDto.addOrderProductDto(orderProductDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        Member savedMember = order.getMember();

        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
            return false;
        }

        return true;
    }

    public void cancelOrder(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId).orElseThrow(Exception::new);
        order.cancelOrder();
    }

    public Long orders(List<OrderDto> orderDtoList, String email){
        Member member = memberRepository.findByEmail(email);
        List<OrderProduct> orderProductList = new ArrayList<>();

        for(OrderDto orderDto : orderDtoList){
            Product product = ProductRepository.findProductId(orderDto.getProductId());

            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(member, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }
}