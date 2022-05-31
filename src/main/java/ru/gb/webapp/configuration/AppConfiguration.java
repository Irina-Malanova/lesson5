package ru.gb.webapp.configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import ru.gb.webapp.entities.ProductItem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.context.annotation.Configuration
public class AppConfiguration {
    private static SessionFactory sessionFactory;

    @Bean
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        }

        return sessionFactory;
    }

    @Bean
    public static Session initDb() {
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            String sql = Files.lines(Paths.get("full.sql")).collect(Collectors.joining(" "));
            session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();

            List<ProductItem> productList = session.createQuery("from ProductItem").getResultList();
            for (ProductItem item : productList) {
                System.out.println(String.format("SimpleItem [id = %d, title = %s, price = %d]", item.getId(), item.getTitle(), item.getCost()));
            }
            session.getTransaction().commit();
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return session;
    }

}
