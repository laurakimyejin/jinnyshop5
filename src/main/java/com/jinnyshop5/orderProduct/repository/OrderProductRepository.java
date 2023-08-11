package com.jinnyshop5.orderProduct.repository;

import com.jinnyshop5.orderProduct.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
