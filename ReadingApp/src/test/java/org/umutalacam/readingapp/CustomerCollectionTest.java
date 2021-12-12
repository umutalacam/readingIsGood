package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerRepository;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.system.exception.RestException;

@SpringBootTest
public class CustomerCollectionTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void customerUpdateTest() throws RestException {
        Customer customer = new Customer();
        customer.setUsername("john.doe");
        customer.setPassword("penguen");
        customerService.updateCustomer("john.doe", customer);
    }

    @Test
    public void readsFirstPage() {

    }
}
