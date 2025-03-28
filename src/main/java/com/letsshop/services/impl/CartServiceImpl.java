package com.letsshop.services.impl;

import com.letsshop.dto.CartDTO;
import com.letsshop.dto.CartItemDTO;
import com.letsshop.entities.Cart;
import com.letsshop.entities.CartItem;
import com.letsshop.entities.Product;
import com.letsshop.repository.CartItemRepository;
import com.letsshop.repository.CartRepository;
import com.letsshop.repository.ProductRepository;
import com.letsshop.services.interfac.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDTO getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        return convertToDTO(cart);
    }

    @Override
    public CartDTO addToCart(Long cartId, Long productId, int quantity) {
        Cart cart = getCartEntity(cartId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the item already exists in cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
            cartItemRepository.save(item);
        }

        return convertToDTO(cartRepository.save(cart));
    }

    @Override
    public CartDTO removeFromCart(Long cartId, Long productId) {
        Cart cart = getCartEntity(cartId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return convertToDTO(cartRepository.save(cart));
    }

    private Cart getCartEntity(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    // Convert Entity to DTO
    private CartDTO convertToDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getId());
        dto.setItems(cart.getItems().stream().map(item -> {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setPrice(item.getProduct().getPrice());
            itemDTO.setQuantity(item.getQuantity());
            return itemDTO;
        }).collect(Collectors.toList()));
        return dto;
    }

}

