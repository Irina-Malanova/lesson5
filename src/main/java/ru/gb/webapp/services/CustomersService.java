package ru.gb.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.webapp.dao.CustomersDao;
import ru.gb.webapp.entities.Customer;
import ru.gb.webapp.entities.CustomersDetail;
import ru.gb.webapp.model.Orders;

import java.util.List;

@Service
public class CustomersService {
    private final CustomersDao customersDao;

    @Autowired
    public CustomersService(CustomersDao customersDao) {
        this.customersDao = customersDao;
    }

    public List<Customer> findAll() {
        return customersDao.findAll();
    }

    public void save(String title, String email, String address) {
        Customer customer = new Customer();
        customer.setName(title);
        CustomersDetail customersDetails = new CustomersDetail();
        customersDetails.setCity(address);
        customersDetails.setEmail(email);
        customersDetails.setCustomer(customer);
        customer.setCustomersDetail(customersDetails);
        customersDao.save(customer);
    }

    public CustomersDetail findById(Long id) {
        return customersDao.getCustomerDetailsById(id);
    }

    public void delete(Long id) {
        customersDao.delete(id);
    }

    public List<Orders> showOrderListByCustomerId(Long id) {
        return customersDao.findOrdersForCustomerByCustomerId(id);
    }
}
