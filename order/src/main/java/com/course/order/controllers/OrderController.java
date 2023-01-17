package com.course.order.controllers;

import com.course.order.domain.Orders;
import com.course.order.domain.OrderItem;
import com.course.order.repositories.OrderItemRepository;
import com.course.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @GetMapping(value = "/orders")
    public List<Orders> list() {
        return orderRepository.findAll();
    }

    @PostMapping(value = "/order")
    public ResponseEntity<Orders> createNewOrder(@RequestBody Orders products) {
        //Order order = new Order();
        Orders order = orderRepository.save(new Orders());
        for(OrderItem item : products.getProducts())
            order.addProduct(new OrderItem(item.getProductId(), item.getQuantity(), item.getTotalPrice()));
        orderRepository.save(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PostMapping(value = "/order/{orderId}")
    @Transactional
    public ResponseEntity<OrderItem> addProductToOrder(@PathVariable Long orderId, @RequestBody OrderItem product) {
        Orders order = orderRepository.getOne(orderId);
        order.addProduct(product);
        orderRepository.save(order);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}