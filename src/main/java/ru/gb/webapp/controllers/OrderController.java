package ru.gb.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.webapp.services.OrderService;


@Controller
@EnableAutoConfiguration
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/show/{customerId}")
    public String showOrdersPage(Model model, @PathVariable long customerId) {
        model.addAttribute("ordersList", orderService.findOrdersForCustomerByCustomerId(customerId));
        return "ordersList";
    }

    @GetMapping(value = "/show/{orderNummer}/{customerId}")
    public String showProductsPage(Model model, @PathVariable int orderNummer, @PathVariable long customerId) {

        model.addAttribute("orderList", orderService.showOrder(orderNummer, customerId));
        return "order";
    }

    @GetMapping(value = "/delete/{orderNummer}/{customerId}")
    public String deleteOrderForCustomer(Model model, @PathVariable int orderNummer, @PathVariable long customerId) {
        orderService.deleteOrder(orderNummer, customerId);
        return "redirect:/order/show/" + customerId;
    }

    @GetMapping(value = "/delete/{orderNummer}/{customerId}/{productId}")
    public String deleteProductFromOrder(Model model, @PathVariable int orderNummer, @PathVariable long customerId, @PathVariable long productId) {
        orderService.deleteProductFromOrder(orderNummer, customerId, productId);
        return "redirect:/order/show/" + customerId;
    }

    @GetMapping(value = "/create/{customerId}")
    public String createOrder(Model model, @PathVariable long customerId) {
        return "create_order";
    }

    // POST http://localhost:8189/app/product/create
    @PostMapping(value = "/create")
    public String createOrder(@RequestParam int nummer,
                              @RequestParam Long customerId,
                              @RequestParam Long productId) {

        orderService.addProduct(nummer, customerId, productId);

        return "redirect:/customer/show_all";
    }
}
