package com.example.electronico.service;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.Item;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart update(Cart cart) throws ProductNotFoundException {
        if(cartRepository.existsById(cart.getCartId())) {
            cart.setTotal(calculateTotal(cart.getItems()));
            return cartRepository.save(cart);
        }
        throw new ProductNotFoundException();
    }

    public double calculateTotal(List<Item> items) {
        double total = 0;
        for(Item item : items) {
            total+= ((item.getProduct().getPrice()) * item.getQuantity());
        }
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }
}
