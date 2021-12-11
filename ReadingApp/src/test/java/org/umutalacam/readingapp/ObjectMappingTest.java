package org.umutalacam.readingapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.umutalacam.readingapp.customer.Customer;

import java.util.HashMap;
import java.util.Map;

public class ObjectMappingTest {
    @Test
    public void testCustomerMapping() {
        Customer c1 = new Customer();
        c1.setUsername("Penguin");
        c1.setEmail("email");
        c1.setFirstName("fname");
        c1.setLastName("aç kapıyı");

        Customer c2 = new Customer();
        c2.setPassword("deneme");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> newCustomerMap = mapper.convertValue(c2, Map.class);
        Map<String, Object> oldCustomerMap = mapper.convertValue(c1, Map.class);

        for (String key : newCustomerMap.keySet()) {
            if (oldCustomerMap.containsKey(key)) {
                oldCustomerMap.put(key, newCustomerMap.get(key));
            }
        }
        Customer finalResult =  mapper.convertValue(oldCustomerMap, Customer.class);
        System.out.println(finalResult.toString());

    }
}
