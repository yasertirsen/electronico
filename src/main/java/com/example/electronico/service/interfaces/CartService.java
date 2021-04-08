package com.example.electronico.service.interfaces;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.model.Cart;

public interface CartService {
    Cart update(Cart cart) throws ProductNotFoundException;
}
