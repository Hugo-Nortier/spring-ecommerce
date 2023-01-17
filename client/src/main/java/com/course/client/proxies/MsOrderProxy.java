package com.course.client.proxies;

import com.course.client.beans.OrderBean;
import com.course.client.beans.OrderItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "ms-order", url = "localhost:8093")
public interface MsOrderProxy {
    @PostMapping(value = "/order")
    ResponseEntity<OrderBean> createNewOrder(@RequestBody OrderBean orderData);

    @PostMapping(value = "/order/{orderId}")
    ResponseEntity<OrderItemBean> addProductToOrder(@PathVariable Long orderId, @RequestBody OrderItemBean orderItem);

    @GetMapping(value = "/orders")
    List<OrderBean> list();
}
