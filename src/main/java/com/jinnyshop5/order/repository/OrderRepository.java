package com.jinnyshop5.order.repository;

import com.jinnyshop5.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
