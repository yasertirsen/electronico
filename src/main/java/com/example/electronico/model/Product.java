package com.example.electronico.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String title;
    private String manufacturer;
    private Double price;
    @Lob
    private String description;
    @Lob
    private byte[] image;
    private String category;
    private Integer stock;
}
