package org.umutalacam.readingapp.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.exception.CustomerNotFoundException;
import org.umutalacam.readingapp.system.exception.RestException;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
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

    public Customer updateCustomer(String username, Customer customer) throws RestException {
        // Check if customer exists
        Optional<Customer> optCustomer = this.customerRepository.findCustomerByUsername(username);
        if (optCustomer.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        // Map new values to existing customer
        Customer oldCustomer = optCustomer.get();

        customer.setCustomerId(oldCustomer.getCustomerId());
        if (customer.getUsername() == null) customer.setUsername(oldCustomer.getUsername());
        if (customer.getFirstName() == null) customer.setFirstName(oldCustomer.getFirstName());
        if (customer.getLastName() == null) customer.setLastName(oldCustomer.getLastName());
        if (customer.getEmail() == null) customer.setEmail(oldCustomer.getEmail());
        if (customer.getPassword() == null) customer.setPassword(oldCustomer.getPassword());

        return this.customerRepository.save(customer);
    }

    private Customer aggregateCustomers(Customer c1, Customer c2) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> newCustomerMap = mapper.convertValue(c2, Map.class);
        Map<String, Object> oldCustomerMap = mapper.convertValue(c1, Map.class);

        for (String key : newCustomerMap.keySet()) {
            if (oldCustomerMap.containsKey(key)) {
                oldCustomerMap.put(key, newCustomerMap.get(key));
            }
        }
        return mapper.convertValue(oldCustomerMap, Customer.class);
    }
}
