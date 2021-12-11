package org.umutalacam.readingapp.customer;

import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.exception.CustomerNotFoundException;
import org.umutalacam.readingapp.system.exception.RestException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Returns all customers
     * @return
     */
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    /**
     * Returns the customer with the given username
     * @param username Username of the customer
     * @return Customer that have the given username
     * @throws RestException
     */
    public Customer getCustomerByUsername(String username) throws RestException {
        Optional<Customer> customer = this.customerRepository.findCustomerByUsername(username);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist.");
        }
        return customer.get();
    }

    public Customer createCustomer(Customer customer) {
        // Todo validation
        return this.customerRepository.save(customer);
    }

    public Customer updateCustomer() {
        return null;
    }
}
