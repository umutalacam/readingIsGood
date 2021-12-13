package org.umutalacam.readingapp.customer;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.exception.CustomerNotFoundException;
import org.umutalacam.readingapp.customer.exception.DuplicateRecordException;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Returns all customers
     * @return List of customers
     */
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    /**
     * Returns a page of customers
     * @param pageIndex page number
     * @param pageSize number of elements in a page
     * @return Page of customers
     */
    public Page<Customer> getCustomerPage(int pageIndex, int pageSize) throws RestException {
        if (pageIndex < 0)
            throw new RestException("Page index can't be smaller than 0.", HttpStatus.BAD_REQUEST, null);
        if (pageSize <= 0)
            throw new RestException("Page size can't be smaller than 1", HttpStatus.BAD_REQUEST, null);
        return this.customerRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    /**
     * Returns the customer with the given username
     * @param username Username of the customer
     * @return Customer that have the given username
     * @throws RestException thrown if the validation failed.
     */
    public Customer getCustomerByUsername(String username) throws RestException {
        Optional<Customer> customer = this.customerRepository.findCustomerByUsername(username);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer does not exist.");
        }
        return customer.get();
    }

    /**
     * Validates the customer data and creates customer on the db.
     * @param customer Customer object that will be saved
     * @return Saved customer object, including insertion ID.
     * @throws RestException thrown if validation failed.
     */
    public Customer createCustomer(Customer customer) throws RestException {
        CustomerValidationUtil.getInstance().validateCustomer(customer);
        try {
            // Encode password
            String encodedPassword = encodePassword(customer.getPassword());
            customer.setPassword(encodedPassword);
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

    /**
     * Update customer object by username
     * @param username oldUsername
     * @param customer The customer object that has updated fields.
     * @return Updated customer
     * @throws RestException Rest response exceptions
     */
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
        else customer.setPassword(encodePassword(customer.getPassword()));
        return this.customerRepository.save(customer);
    }

    private String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
