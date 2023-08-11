package com.jinnyshop5.OrderProduct.repository;

import com.jinnyshop5.OrderProduct.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
