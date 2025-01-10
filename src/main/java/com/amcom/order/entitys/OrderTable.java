package com.amcom.order.entitys;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;
    @OneToMany
    private List<Product> products = new ArrayList<>(); ;

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }

    public double calculateTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

}

