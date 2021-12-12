package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.order.Order;
import org.umutalacam.readingapp.order.OrderRepository;
import org.umutalacam.readingapp.order.OrderService;
import org.umutalacam.readingapp.order.OrderStatus;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.List;

@SpringBootTest
public class OrderCollectionTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;

    @Test
    public void findByExampleTest() throws RestException {
        Page<Order> orders = orderService.getOrdersPageByStatus(OrderStatus.REJECTED, 0, 6);
        for (Order o: orders) {
            System.out.println(o);
        }
    }
}
