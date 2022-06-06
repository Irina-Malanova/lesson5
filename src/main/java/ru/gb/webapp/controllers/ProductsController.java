package ru.gb.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.webapp.services.ProductsService;

@Controller
@EnableAutoConfiguration
@RequestMapping("/product")
public class ProductsController {
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping(value = "/info")
    @ResponseBody
    public String info() {
        return "info";
    }

    // GET http://localhost:8189/app/product/show_all
    @GetMapping(value = "/show_all")
    public String showProductsPage(Model model) {

        model.addAttribute("productList", productsService.findAll());
        return "productList";
    }

    // GET http://localhost:8189/app/product/show/{id}
    @GetMapping(value = "/show/{id}")
    public String showProductPageById(Model model, @PathVariable Long id) {
        model.addAttribute("product", productsService.findById(id));
        return "product_info";
    }

    @GetMapping(value = "/plus/{id}")
    public String plusProductCostById(Model model, @PathVariable Long id) {
        productsService.plus(productsService.findById(id));
        return "redirect:/product/show_all";
    }

    @GetMapping(value = "/minus/{id}")
    public String minusProductCostById(Model model, @PathVariable Long id) {
        productsService.minus(productsService.findById(id));
        return "redirect:/product/show_all";
    }

    // GET http://localhost:8189/app/create
    @GetMapping(value = "/create")
    public String createProduct() {
        return "create_product";
    }

    // POST http://localhost:8189/app/product/create
    @PostMapping(value = "/create")
    public String saveProduct(@RequestParam String title, @RequestParam int cost) {

        productsService.save(title, cost);
        return "redirect:/product/show_all";
    }

    // delete http://localhost:8189/app/product/delete
    @GetMapping(value = "/delete/{id}")
    public String deleteProduct(Model model, @PathVariable Long id) {
        productsService.delete(id);
        return "redirect:/product/show_all";
    }
}
