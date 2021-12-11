package org.umutalacam.readingapp.order;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.customer.Customer;

import java.util.List;

@Data
@Document("order")
public class Order {
    @Id
    private int order_id;
    private Customer customer;
    private List<Book> books;
}
