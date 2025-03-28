package com.letsshop.controller;

import com.letsshop.dto.CartDTO;
import com.letsshop.services.interfac.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*") // Allow Frontend to Connect
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<CartDTO> addToCart(@PathVariable Long cartId,
                                             @RequestParam Long productId,
                                             @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addToCart(cartId, productId, quantity));
    }

    @DeleteMapping("/{cartId}/remove")
    public ResponseEntity<CartDTO> removeFromCart(@PathVariable Long cartId,
                                                  @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeFromCart(cartId, productId));
    }
}
