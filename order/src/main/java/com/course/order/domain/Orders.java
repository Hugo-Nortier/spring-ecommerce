package com.course.order.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> products = new ArrayList<>();

    public Orders(Long id) {
        this.id = id;
    }
    public Orders() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<OrderItem> getProducts() {
        return products;
    }
    public void addProduct(OrderItem product) {
        this.products.add(product);
    }
}
