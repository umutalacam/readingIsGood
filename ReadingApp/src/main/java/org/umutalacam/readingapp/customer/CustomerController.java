package org.umutalacam.readingapp.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.HashMap;
import java.util.List;

@RestController
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping("/customer/{username}")
    public Customer getCustomer(@PathVariable String username) throws RestException {
        return this.customerService.getCustomerByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        Customer savedCustomer = this.customerService.createCustomer(customer);
        // Build response
        HashMap<String, Object> savedCustomerResponse = new HashMap<>();
        savedCustomerResponse.put("message", "Customer created successfully.");
        savedCustomerResponse.put("customer", savedCustomer);
        return ResponseEntity.ok(savedCustomerResponse);
    }

    @PutMapping("/customer/{username}")
    public ResponseEntity<?> updateCustomer(@PathVariable String username, @RequestBody Customer customer) throws RestException {
        Customer updatedCustomer = this.customerService.updateCustomer(username, customer);
        // Build response
        HashMap<String, Object> updatedCustomerResponse = new HashMap<>();
        updatedCustomerResponse.put("message", "Customer updated successfully.");
        updatedCustomerResponse.put("customer", updatedCustomer);
        return ResponseEntity.ok(updatedCustomerResponse);
    }


}
