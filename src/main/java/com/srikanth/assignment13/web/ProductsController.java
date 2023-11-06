package com.srikanth.assignment13.web;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srikanth.assignment13.domain.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductsController {
//	public record Product(Integer id, String name, BigDecimal price) {}


    private List<Product> allProducts = new ArrayList<>();

    public ProductsController() {
        allProducts.add(new Product(1, "Product #1", new BigDecimal("19.99")));
        allProducts.add(new Product(2, "Product #2", new BigDecimal("29.99")));
        allProducts.add(new Product(3, "Product #3", new BigDecimal("39.99")));
        allProducts.add(new Product(4, "Product #4", new BigDecimal("49.99")));
        allProducts.add(new Product(5, "Product #5", new BigDecimal("59.99")));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(allProducts);
    }
}