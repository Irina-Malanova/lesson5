package ru.gb.webapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.gb.webapp.entities.Customer;
import ru.gb.webapp.entities.Order;
import ru.gb.webapp.entities.Product;
import ru.gb.webapp.model.Orders;

import java.util.List;

@Component
public class OrderDao {
    @Autowired
    @Qualifier("getSessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void addProduct(int orderNummer, Customer customer, Product product) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        Order order = new Order();
        order.setPrice(product.getCost());
        order.setProduct(product);
        order.setCustomer(customer);
        order.setOrderNummer(orderNummer);
        order.setProduct(product);
        order.setCustomer(customer);
        session.save(order);
        transaction.commit();
    }

    public void deleteOrder(int orderNummer, long customerId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Order a where a.orderNummer=" +
                orderNummer + " and a.customer=" + customerId).executeUpdate();
        transaction.commit();

    }

    public List<Order> showOrder(int orderNummer, long customerId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orderList = session.createQuery("SELECT a FROM Order a where a.orderNummer=" +
                orderNummer + " and a.customer=" + customerId, Order.class).getResultList();
        transaction.commit();
        return orderList;
    }

    public void deleteProductFromOrder(int orderNummer, long customerId, long productId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.createQuery("DELETE FROM Order a where a.orderNummer=" +
                orderNummer + "and a.customer=" + customerId +
                " and a.product.id=" + productId).executeUpdate();
        transaction.commit();
    }

    public List<Orders> findOrdersForCustomerByCustomerId(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Orders> ordersList = session.createQuery("SELECT NEW ru.gb.webapp.model.Orders(a.orderNummer, COUNT(a) as productsCount, a.customer.id) FROM Order a where a.customer.id=" + id + " group by a.orderNummer",
                Orders.class).getResultList();
        transaction.commit();
        return (ordersList);
    }
}
