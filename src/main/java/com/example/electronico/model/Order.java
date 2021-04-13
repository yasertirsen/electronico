package com.example.electronico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    @ManyToOne
    @JoinColumn(referencedColumnName = "paymentId", name = "paymentId")
    private Payment paymentMethod;
    @ManyToMany
    @JoinColumn(referencedColumnName = "itemId", name = "itemId")
    private List<Item> items;
    private double total;
    private String date;
}
