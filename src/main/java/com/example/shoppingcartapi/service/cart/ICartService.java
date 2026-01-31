package com.example.shoppingcartapi.service.cart;

import com.example.shoppingcartapi.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initNewCart();

    Cart getCartByUserId(Long userId);
}
