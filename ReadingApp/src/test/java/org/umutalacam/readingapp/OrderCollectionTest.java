package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.umutalacam.readingapp.order.Order;
import org.umutalacam.readingapp.order.OrderRepository;
import org.umutalacam.readingapp.order.OrderService;
import org.umutalacam.readingapp.order.OrderStatus;

import java.util.List;

@SpringBootTest
public class OrderCollectionTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    public void findByExampleTest() {

        Order exampleOrder = new Order();
        exampleOrder.setStatus(OrderStatus.PENDING);
        Example<Order> orderExample = Example.of(exampleOrder);
        List<Order> orders = orderRepository.findAll(Example.of(exampleOrder));
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
