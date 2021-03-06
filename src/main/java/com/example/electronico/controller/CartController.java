package com.example.electronico.controller;

import com.example.electronico.exception.ElectronicoExceptionHandler;
import com.example.electronico.exception.NotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.Order;
import com.example.electronico.model.User;
import com.example.electronico.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController extends ElectronicoExceptionHandler {

    private final CartServiceImpl cartService;

    @Autowired
    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/update")
    public Cart update(@RequestBody Cart cart) throws NotFoundException {
        return cartService.update(cart);
    }

    @PostMapping("/pay/{userId}")
    public User pay(@RequestBody Order order, @PathVariable Long userId) throws UserNotFoundException {
        return cartService.pay(order, userId);
    }
}
