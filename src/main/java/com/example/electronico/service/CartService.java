package com.example.electronico.service;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.exception.UserNotFoundException;
import com.example.electronico.model.*;
import com.example.electronico.repository.CartRepository;
import com.example.electronico.repository.ProductRepository;
import com.example.electronico.repository.UserRepository;
import com.example.electronico.service.pattern.CrudTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService extends CrudTemplate<Cart> {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        super(cartRepository);
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart update(Cart cart) throws NotFoundException {
        if(cartRepository.existsById(cart.getCartId())) {
            cart.setTotal(calculateTotal(cart.getItems()));
            return cartRepository.save(cart);
        }
        throw new NotFoundException();
    }

    public User pay(Order order, Long userId) throws UserNotFoundException {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        order.setDate(dtf.format(LocalDateTime.now()));

        //add order to purchase history & update user cart
        user.getPurchaseHistory().add(order);
        user.getCart().setItems(new ArrayList<>());
        user.getCart().setTotal(0.0);

        List<Product> updatedProducts = new ArrayList<>();
        Product product;

        //update stock
        for(Item item: order.getItems()) {
            product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            updatedProducts.add(product);
        }

        productRepository.saveAll(updatedProducts);

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
