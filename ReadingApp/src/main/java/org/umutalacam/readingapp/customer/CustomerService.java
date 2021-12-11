package org.umutalacam.readingapp.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.exception.CustomerNotFoundException;
import org.umutalacam.readingapp.customer.exception.DuplicateRecordException;
import org.umutalacam.readingapp.system.exception.RestException;

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

    public Customer createCustomer(Customer customer) throws RestException {
        CustomerValidationUtil.getInstance().validateCustomer(customer);
        try {
            return this.customerRepository.save(customer);
        } catch (DuplicateKeyException exception) {
            String message = exception.getMessage();
            if (message != null) {
                if (message.contains("username"))
                    throw new DuplicateRecordException("Username already exists.");
                else if (message.contains("email"))
                    throw new DuplicateRecordException("Email already exists.");
            }
            throw new DuplicateRecordException("Duplicate records.");
        }
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
