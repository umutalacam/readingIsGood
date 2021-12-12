package org.umutalacam.readingapp.customer;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.response.PaginatedResponse;

import java.util.HashMap;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public PaginatedResponse<Customer> getAllCustomers(@RequestParam(defaultValue = "1") int p,
                                                       @RequestParam(defaultValue = "5") int pageSize) throws RestException {
        Page<Customer> customerPage = this.customerService.getCustomerPage(p, pageSize);
        PaginatedResponse<Customer> response = new PaginatedResponse<>();
        response.setCurrentPage(p);
        response.setPageSize(pageSize);
        response.setTotalPages(customerPage.getTotalPages());
        response.setTotalRecords(customerPage.getTotalElements());
        response.setRecords(customerPage.getContent());
        return response;
    }

    @GetMapping("/customer/{username}")
    public Customer getCustomer(@PathVariable String username) throws RestException {
        return this.customerService.getCustomerByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) throws RestException {
        Customer savedCustomer = this.customerService.createCustomer(customer);
        // Build response
        HashMap<String, Object> savedCustomerResponse = new HashMap<>();
        savedCustomerResponse.put("message", "Customer created successfully.");
        savedCustomerResponse.put("customer", savedCustomer.getCustomerId());
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
