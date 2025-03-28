package com.letsshop.services.interfac;

import com.letsshop.dto.CartDTO;

public interface CartService {
    CartDTO getCart(Long cartId);
    CartDTO addToCart(Long cartId, Long productId, int quantity);
    CartDTO removeFromCart(Long cartId, Long productId);
}
