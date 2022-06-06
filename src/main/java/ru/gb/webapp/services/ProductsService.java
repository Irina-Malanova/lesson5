package ru.gb.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.webapp.dao.ProductsDao;
import ru.gb.webapp.entities.Product;

import java.util.List;

@Service
public class ProductsService {
    private final ProductsDao productsRepository;

    @Autowired
    public ProductsService(ProductsDao productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    public void save(String title, int cost) {
        Product product = new Product();
        product.setCost(cost);
        product.setTitle(title);
        productsRepository.save(product);
    }

    public void plus(Product product) {
        product.setCost(product.getCost() + 1);
        productsRepository.save(product);
    }

    public void minus(Product product) {
        if (product.getCost() > 0) {
            product.setCost(product.getCost() - 1);
        }
        productsRepository.save(product);
    }

    public Product findById(Long id) {
        return productsRepository.findById(id);
    }

    public void delete(Long id) {
        productsRepository.delete(id);
    }
}
