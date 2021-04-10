package com.example.electronico.service;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.Cart;
import com.example.electronico.model.Item;
import com.example.electronico.model.Order;
import com.example.electronico.model.User;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.interfaces.CrudStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService implements CrudStrategy<Cart> {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cart add(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart get(Long cartId) throws NotFoundException {
        return cartRepository.findById(cartId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart update(Cart cart) throws NotFoundException {
        if(cartRepository.existsById(cart.getCartId())) {
            cart.setTotal(calculateTotal(cart.getItems()));
            return cartRepository.save(cart);
        }
        throw new NotFoundException();
    }

    @Override
    public ResponseEntity<String> delete(Long cartId) throws NotFoundException {
        cartRepository.deleteById(cartId);
        return new ResponseEntity<>("Cart deleted, id: " + cartId, HttpStatus.OK);
    }

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
