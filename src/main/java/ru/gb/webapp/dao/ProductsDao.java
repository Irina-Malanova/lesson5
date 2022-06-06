package ru.gb.webapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.gb.webapp.entities.Product;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Component
public class ProductsDao {
    private static final String sql = "SELECT a FROM Product a where a.id=";
    @Autowired
    @Qualifier("getSessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Product> findAll() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Product> productList = getSession().createQuery("from Product").getResultList();
        transaction.commit();
        if (productList.isEmpty()) {
            return Collections.emptyList();
        }
        return productList;
    }

    public void save(Product product) {
        Long id = product.getId();
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Product productFind = session.createQuery(sql + id, Product.class).getSingleResult();
            productFind.setCost(product.getCost());
            session.save(productFind);
        } catch (NoResultException e) {
            session.save(new Product(product.getTitle(), product.getCost()));
        }

        transaction.commit();
    }

    public void delete(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Product productFind = session.createQuery(sql + id, Product.class).getSingleResult();
        if (productFind != null) {
            session.delete(productFind);
        }
        transaction.commit();
    }

    public Product findById(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Product productItem = session.createQuery(sql + id, Product.class).getSingleResult();
        transaction.commit();
        return (productItem);
    }
}
