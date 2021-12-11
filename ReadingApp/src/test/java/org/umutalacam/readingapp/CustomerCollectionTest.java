package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.system.exception.RestException;

@SpringBootTest
public class CustomerCollectionTest {
    @Autowired
    private CustomerService customerService;

    @Test
    public void customerUpdateTest() throws RestException {
        Customer customer = new Customer();
        customer.setUsername("john.doe");
        customer.setPassword("penguen");
        customerService.updateCustomer("john.doe", customer);
    }
}
