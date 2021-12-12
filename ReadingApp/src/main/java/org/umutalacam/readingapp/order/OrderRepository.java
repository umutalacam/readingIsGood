package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrdersByCustomer_Username(String customer_username);
    List<Order> findOrdersByCustomer_UsernameAndStatus(String customer_username, OrderStatus status);
}
