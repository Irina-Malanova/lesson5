package ru.gb.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.webapp.services.CustomersService;

@Controller
@EnableAutoConfiguration
@RequestMapping("/customer")
public class CustomerController {
    private final CustomersService customersService;

    @Autowired
    public CustomerController(CustomersService customersService) {
        this.customersService = customersService;
    }

    // GET http://localhost:8189/app/customer/show_all
    @GetMapping(value = "/show_all")
    public String showProductsPage(Model model) {

        model.addAttribute("customerList", customersService.findAll());
        return "customerList";
    }

    // GET http://localhost:8189/app/customer/show/{id}
    @GetMapping(value = "/show/{id}")
    public String showCustomerPageById(Model model, @PathVariable Long id) {
        model.addAttribute("customer", customersService.findById(id));
        return "customer_info";
    }

    // GET http://localhost:8189/app/customer/delete/{id}
    @GetMapping(value = "/delete/{id}")
    public String deleteCustomerById(Model model, @PathVariable Long id) {
        customersService.delete(id);
        return "redirect:/customer/show_all";
    }

    // GET http://localhost:8189/app/create
    @GetMapping(value = "/create")
    public String createCustomer() {
        return "create_customer";
    }

    // POST http://localhost:8189/app/product/create
    @PostMapping(value = "/create")
    public String saveCustomer(@RequestParam String title,
                               @RequestParam String email,
                               @RequestParam String address) {
        customersService.save(title, email, address);
        return "redirect:/customer/show_all";
    }

    // GET http://localhost:8189/app/customer/getOrderList/{id}
    @GetMapping(value = "/getOrderList/{id}")
    public String showOrderListById(Model model, @PathVariable Long id) {
        model.addAttribute("ordersList", customersService.showOrderListByCustomerId(id));
        return "ordersList";
    }
}
