package com.course.product.controllers;

import com.course.product.domain.Product;
import com.course.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = "/products")
    public List<Product> list() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/product/{productId}")
    public Optional<Product> get(@PathVariable Long productId) {
        Optional<Product> productInstance = productRepository.findById(productId);
        if (!productInstance.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Specified product doesn't exist");
        return productInstance;
    }
}