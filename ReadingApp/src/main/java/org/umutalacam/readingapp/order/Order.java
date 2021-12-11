package org.umutalacam.readingapp.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.umutalacam.readingapp.customer.Customer;

import java.util.List;

@Data
@Document("order")
public class Order {
    @Id
    private String orderId;
    private OrderStatus status;
    @DBRef
    private Customer customer;
    private List<BookOrder> items;
    private double totalPrice;

    public Order() {
        status = OrderStatus.PENDING;
    }
}
