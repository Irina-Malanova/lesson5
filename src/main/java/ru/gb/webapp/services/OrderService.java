package ru.gb.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.webapp.dao.CustomersDao;
import ru.gb.webapp.dao.OrderDao;
import ru.gb.webapp.dao.ProductsDao;
import ru.gb.webapp.entities.Customer;
import ru.gb.webapp.entities.Order;
import ru.gb.webapp.entities.Product;
import ru.gb.webapp.model.Orders;

import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;
    @Autowired
    private CustomersDao customersDao;
    @Autowired
    private ProductsDao productsDao;

    @Autowired
    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void addProduct(int orderNummer, long customerId, long productId) {

        Customer customer = customersDao.findById(customerId);
        Product product = productsDao.findById(productId);
        orderDao.addProduct(orderNummer, customer, product);
    }

    public void deleteOrder(int orderNummer, long customerId) {
        orderDao.deleteOrder(orderNummer, customerId);
    }

    public void deleteProductFromOrder(int orderNummer, long customerId, long productId) {
        orderDao.deleteProductFromOrder(orderNummer, customerId, productId);
    }

    public List<Order> showOrder(int orderNummer, long customerId) {
        return orderDao.showOrder(orderNummer, customerId);
    }

    public List<Orders> findOrdersForCustomerByCustomerId(long customerId) {
        return orderDao.findOrdersForCustomerByCustomerId(customerId);
    }
}
