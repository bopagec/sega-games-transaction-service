package com.sega.game.transaction.service;

import com.sega.game.transaction.domain.Product;
import com.sega.game.transaction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> findById(long productId) {
        return productRepository.findById(productId);
    }
}
