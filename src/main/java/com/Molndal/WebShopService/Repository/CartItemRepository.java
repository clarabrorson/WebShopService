package com.Molndal.WebShopService.Repository;

import com.Molndal.WebShopService.Models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
    
}
