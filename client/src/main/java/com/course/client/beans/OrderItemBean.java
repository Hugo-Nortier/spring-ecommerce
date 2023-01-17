package com.course.client.beans;

import java.util.ArrayList;

public class OrderItemBean {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;

    public OrderItemBean() {
    }

    public OrderItemBean(Long cartItemId, Long productId, Integer productQuantity, Double totalPrice) {
        this.id = cartItemId;
        this.productId = productId;
        this.quantity = productQuantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getTotalPrice(){
        return totalPrice;
    }
    public ProductBean getProductBean(ArrayList<ProductBean> products) throws Exception {
        for(ProductBean product : products){
            if(product.getId()==this.productId) return product;
        }
        throw new Exception("error while getting product bean");
    }

    @Override
    public String toString() {
        return "Order Item :"+id+":"+productId+":"+quantity;
    }

}
