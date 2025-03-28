package com.letsshop.controller;

import com.letsshop.dto.CartItemDTO;
import com.letsshop.services.interfac.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin("*")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItemById(@PathVariable Long id) {
        return ResponseEntity.ok(cartItemService.getCartItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemDTO> updateCartItem(@PathVariable Long id, @RequestParam int quantity) {
        return ResponseEntity.ok(cartItemService.updateCartItem(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("CartItem deleted successfully");
    }
}
