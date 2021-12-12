package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.umutalacam.readingapp.customer.Customer;

import java.util.Date;

public interface OrderRepository extends MongoRepository<Order, String> {
    Page<Order> findAllByStatus(OrderStatus status, PageRequest pageRequest);
    Page<Order> findAllByCustomer(Customer customer, PageRequest pageRequest);
    Page<Order> findAllByCustomerAndStatus(Customer customer, OrderStatus status, PageRequest pageRequest);

    Page<Order> findAllByOrderTimeBetween(Date orderTime, Date orderTime2, PageRequest pageRequest);
}
