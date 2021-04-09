package com.example.electronico.service.interfaces;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.Order;
import com.example.electronico.model.User;

public interface CartService {
    Cart update(Cart cart) throws ProductNotFoundException;
    User pay(Order order, Long userId) throws UserNotFoundException;
}
