package org.umutalacam.readingapp.customer;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void createCustomer(Customer customer) {
        this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @PostConstruct
    public void createCustomers() {
        Customer test = new Customer();
        test.setName("Bahar");
        test.setEmail("bahar@penguin.com");
        test.setEncodedPassword("23");
        this.customerRepository.save(test);
    }
}
