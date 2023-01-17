package com.course.client.beans;

import java.util.ArrayList;
import java.util.List;

public class OrderBean {
    private Long id;
    private List<OrderItemBean> products = new ArrayList<>();

    public OrderBean(Long id) {
        this.id = id;
    }
    public OrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemBean> getProducts() {
        return products;
    }
    public void addProduct(OrderItemBean product) {
        this.products.add(product);
    }
    public double getTotalPrice(){
        double totalPrice = 0.0;
        for(OrderItemBean item : products) totalPrice+=item.getTotalPrice();
        return totalPrice;
    }
}
