package com.course.client.beans;

import java.util.ArrayList;
import java.util.List;

public class CartItemBean {

    private Long id;

    private Long productId;

    private Integer quantity;

    public CartItemBean() {
    }

    public CartItemBean( Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "Cart Item :"+id+":"+productId+":"+quantity;
    }
    public ProductBean getProductBean(ArrayList<ProductBean> products) throws Exception {
        for(ProductBean product : products){
            if(product.getId()==this.productId) return product;
        }
        throw new Exception("error while getting product bean");
    }

    public Double getTotalPrice(List<ProductBean> products){
        double totalPrice = 0.0;
        for(ProductBean product : products){
            if(product.getId()==this.productId) totalPrice=product.getPrice()*quantity;
        }
        return totalPrice;
    }
}
