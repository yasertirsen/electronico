package com.example.electronico.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private Boolean enabled;
    private String role;
    private String[] authorities;
    private Boolean isLocked;
    private Long expiresIn;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String token;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "cartId")
    private Cart cart;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<Payment> paymentMethods;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "orderId")
    private List<Order> purchaseHistory;
}
