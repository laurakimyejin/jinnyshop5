package com.jinnyshop5.OrderProduct.repository;

import com.jinnyshop5.Order.model.Order;
import com.jinnyshop5.OrderProduct.model.OrderProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}