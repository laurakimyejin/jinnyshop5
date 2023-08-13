package com.jinnyshop5.Cart.repository;

import com.jinnyshop5.Cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);

}
