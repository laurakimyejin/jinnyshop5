package com.jinnyshop5.Order.repository;

import com.jinnyshop5.Order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
