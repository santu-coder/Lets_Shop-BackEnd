package com.letsshop.services.impl;


import com.letsshop.dto.CartItemDTO;
import com.letsshop.entities.CartItem;
import com.letsshop.repository.CartItemRepository;
import com.letsshop.services.interfac.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItemDTO getCartItemById(Long id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        return convertToDTO(cartItem);
    }

    @Override
    public CartItemDTO updateCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItem.setQuantity(quantity);
        return convertToDTO(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new RuntimeException("CartItem not found");
        }
        cartItemRepository.deleteById(id);
    }

    private CartItemDTO convertToDTO(CartItem cartItem) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName());
        dto.setPrice(cartItem.getProduct().getPrice());
        dto.setQuantity(cartItem.getQuantity());
        return dto;
    }
}
