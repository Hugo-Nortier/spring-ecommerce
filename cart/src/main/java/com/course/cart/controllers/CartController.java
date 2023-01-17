package com.course.cart.controllers;

import com.course.cart.domain.Cart;
import com.course.cart.domain.CartItem;
import com.course.cart.repositories.CartItemRepository;
import com.course.cart.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
public class CartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @PostMapping(value = "/cart")
    public ResponseEntity<Cart> createNewCart(@RequestBody Cart cartData) {
        Cart cart = cartRepository.save(new Cart());
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value = "/cart/{cartId}")
    public Optional<Cart> getCart(@PathVariable Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart==null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't get cart");
        return cart;
    }

    @GetMapping(value = "/cartid")
    public Long getCartId() {
        return cartRepository.findAll().size()>0 ? cartRepository.findAll().get(cartRepository.findAll().size()-1).getId() : 1L;
    }

    @PostMapping(value = "/cart/{cartId}")
    @Transactional
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long cartId, @RequestBody CartItem cartItem) {
        Cart cart = cartRepository.getOne(cartId);

        for(CartItem item : cart.getProducts()){
            if(item.getProductId()==cartItem.getProductId()) {
                item.setQuantity(item.getQuantity()+1);
                return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
            }
        }
        cart.addProduct(cartItem);
        cartRepository.save(cart);
        return new ResponseEntity<>(cartItem, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/cart/clear/{cartId}")
    @Transactional
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId){
        cartRepository.findById(cartId).get().getProducts().clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/cart")
    @Transactional
    public ResponseEntity<Cart> updateCart(@RequestBody Cart cart) {
        if (cart == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create a new cart");
        cartRepository.save(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
