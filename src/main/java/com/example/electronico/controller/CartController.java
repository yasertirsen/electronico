package com.example.electronico.controller;

import com.example.electronico.exception.ElectronicoExceptionHandler;
import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
public class CartController extends ElectronicoExceptionHandler {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/update")
    public Cart update(@RequestBody Cart cart) throws ProductNotFoundException {
        return cartService.update(cart);
    }
}
