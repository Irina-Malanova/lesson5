package ru.gb.webapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.gb.webapp.entities.ProductItem;
import ru.gb.webapp.model.Product;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductsDao {
    @Autowired
    @Qualifier("getSessionFactory")
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Product> findAll() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<ProductItem> productList = getSession().createQuery("from ProductItem").getResultList();
        transaction.commit();
        if (productList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Product> products = new ArrayList<>();
        for (ProductItem item : productList) {
            products.add(new Product(item.getId(), item.getTitle(), item.getCost()));
        }
        return products;
    }

    public void save(Product product) {
        Long id = product.getId();
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            ProductItem productFind = session.createQuery("SELECT a FROM ProductItem a where a.id=" + id, ProductItem.class).getSingleResult();
            productFind.setCost(product.getCost());
            session.save(productFind);

        } catch (NoResultException e) {
            session.save(new ProductItem(product.getTitle(), product.getCost()));
        }

        transaction.commit();
    }

    public void delete(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        ProductItem productFind = session.createQuery(
                "SELECT a FROM ProductItem a where a.id=" + id, ProductItem.class).getSingleResult();
        if (productFind != null) {
            session.delete(productFind);
        }
        transaction.commit();
    }

    public Product findById(Long id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        ProductItem productItem = session.createQuery("SELECT a FROM ProductItem a where a.id=" + id, ProductItem.class).getSingleResult();
        transaction.commit();
        return (new Product(productItem.getId(), productItem.getTitle(), productItem.getCost()));
    }
}
