package com.example.electronico.service.pattern.proxy;

import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Item;
import com.example.electronico.model.Order;
import com.example.electronico.model.User;

import java.util.List;

public interface CartService {
    User pay(Order order, Long userId) throws UserNotFoundException;
    double calculateTotal(List<Item> items);
}
