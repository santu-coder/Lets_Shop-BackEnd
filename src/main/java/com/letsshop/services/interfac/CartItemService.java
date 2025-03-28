package com.letsshop.services.interfac;

import com.letsshop.dto.CartItemDTO;

public interface CartItemService {
    CartItemDTO getCartItemById(Long id);
    CartItemDTO updateCartItem(Long id, int quantity);
    void deleteCartItem(Long id);
}
