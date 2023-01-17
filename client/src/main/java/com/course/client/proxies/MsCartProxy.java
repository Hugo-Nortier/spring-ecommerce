package com.course.client.proxies;

import com.course.client.beans.CartBean;
import com.course.client.beans.CartItemBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "ms-cart", url = "localhost:8092")

public interface MsCartProxy {

    @PostMapping(value = "/cart")
    ResponseEntity<CartBean> createNewCart(@RequestBody CartBean cartData);

    @GetMapping(value = "/cart/{cartId}")
    Optional<CartBean> getCart(@PathVariable Long cartId);

    @GetMapping(value = "/cartid")
    Long getCartId();

    @PostMapping(value = "/cart/{cartId}")
    ResponseEntity<CartItemBean> addProductToCart(@PathVariable Long cartId, @RequestBody CartItemBean cartItem);

    @DeleteMapping(value = "/cart/clear/{cartId}")
    ResponseEntity<Void> clearCart(@PathVariable Long cartId);

    @PutMapping(value = "/cart")
    ResponseEntity<CartBean> updateCart(@RequestBody CartBean cart);
}
