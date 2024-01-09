package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Detta interface är en JPA-repository och används för att utföra operationer på tabellen cart_items.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
}
