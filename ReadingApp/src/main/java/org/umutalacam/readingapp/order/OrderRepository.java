package org.umutalacam.readingapp.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrdersByCustomer_Username(String customer_username);
    List<Order> findOrdersByCustomer_UsernameAndStatus(String customer_username, OrderStatus status);
}
