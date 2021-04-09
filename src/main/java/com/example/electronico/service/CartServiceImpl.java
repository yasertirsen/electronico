package com.example.electronico.service;

import com.example.electronico.exception.ProductNotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.Item;
import com.example.electronico.model.Order;
import com.example.electronico.model.User;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart update(Cart cart) throws ProductNotFoundException {
        if(cartRepository.existsById(cart.getCartId())) {
            cart.setTotal(calculateTotal(cart.getItems()));
            return cartRepository.save(cart);
        }
        throw new ProductNotFoundException();
    }

    @Override
    public User pay(Order order, Long userId) throws UserNotFoundException {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        order.setDate(dtf.format(LocalDateTime.now()));
        user.getPurchaseHistory().add(order);
        user.getCart().setItems(new ArrayList<>());
        user.getCart().setTotal(0.0);
        return userRepository.save(user);
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
