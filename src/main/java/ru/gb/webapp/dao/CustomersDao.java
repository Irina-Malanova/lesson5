package ru.gb.webapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.gb.webapp.entities.Customer;
import ru.gb.webapp.entities.CustomersDetail;
import ru.gb.webapp.model.Orders;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Component
public class CustomersDao {
    @Autowired
    @Qualifier("getSessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Customer> findAll() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> customersList = getSession().createQuery("from Customer").getResultList();
        transaction.commit();
        if (customersList.isEmpty()) {
            return Collections.emptyList();
        }
        return customersList;
    }

    public void save(Customer customer) {
        Long id = customer.getId();
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Customer customerFind = session.createQuery("SELECT a FROM Customer a where a.id=" + id,
                    Customer.class).getSingleResult();
            session.save(customerFind);

        } catch (NoResultException e) {
            session.save(customer);
        }
        transaction.commit();
    }

    public void delete(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        ru.gb.webapp.entities.Customer CustomerFind = session.createQuery(
                "SELECT a FROM Customer a where a.id=" + id, Customer.class).getSingleResult();
        if (CustomerFind != null) {
            session.delete(CustomerFind);
        }
        transaction.commit();
    }

    public Customer findById(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.createQuery("SELECT a FROM Customer a where a.id=" + id,
                Customer.class).getSingleResult();
        transaction.commit();
        return (customer);
    }

    public List<Orders> findOrdersForCustomerByCustomerId(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Orders> ordersList = session.createQuery("SELECT NEW ru.gb.webapp.model.Orders(a.orderNummer, COUNT(a) as productsCount, a.customer.id) FROM Order a where a.customer.id=" + id + " group by a.orderNummer",
                Orders.class).getResultList();
        transaction.commit();
        return (ordersList);
    }

    public CustomersDetail getCustomerDetailsById(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.createQuery("SELECT a FROM Customer a where a.id=" + id, Customer.class).getSingleResult();
        CustomersDetail customersDetail = customer.getCustomersDetail();
        transaction.commit();
        return (customersDetail);
    }
}
