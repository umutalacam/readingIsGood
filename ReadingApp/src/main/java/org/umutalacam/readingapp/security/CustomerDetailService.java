package org.umutalacam.readingapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.customer.exception.CustomerNotFoundException;
import org.umutalacam.readingapp.system.exception.RestException;

@Service
public class CustomerDetailService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomerDetailService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // load customer from customer service
        Customer customer;
        try {
            customer = customerService.getCustomerByUsername(s);
            return new CustomerDetails(customer);
        } catch (CustomerNotFoundException e) {
            throw new UsernameNotFoundException("Customer not found.");
        } catch (RestException exception) {
            exception.printStackTrace();
        }

        throw new UsernameNotFoundException("Unable to load customer.");
    }
}
