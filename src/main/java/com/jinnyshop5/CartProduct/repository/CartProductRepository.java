package com.jinnyshop5.CartProduct.repository;

import com.jinnyshop5.Cart.dto.CartDetailDto;
import com.jinnyshop5.CartProduct.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    CartProduct findCartIdAndProductId(Long cartId, Long productId);


    @Query("select new com.jinnyshop5.dto.CartDtailDto(cp.id, p.price,cp.count, pm.imgUrl) " +
            "from CartProduct cp, ProductImg pm " +
            "join cp.product p" +
            "where cp.cart.id = :cartId " +
            "and pm.product.id = p.id " +
            "and pm.repimgYn = 'Y' " +
            "order by cp.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
