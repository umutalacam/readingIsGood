package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.book.BookService;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.order.*;
import org.umutalacam.readingapp.order.request.BookAmountPair;
import org.umutalacam.readingapp.order.request.CreateOrderRequest;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderCollectionTest {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CustomerService customerService;
    @Autowired
    BookService bookService;

    @Test
    public void findByExampleTest() throws RestException {
        Page<Order> orders = orderService.getOrdersPageByStatus(OrderStatus.REJECTED, 0, 6);
        for (Order o: orders) {
            System.out.println(o);
        }
    }

    @Test
    public void placeOrderTest() throws RestException {
        // get customers
        List<Customer> customers =  customerService.getAllCustomers();
        List<Book> books = bookService.getAllBooks();
        List<BookAmountPair> bookOrders = new ArrayList<>();

        int bookLimit = books.size();

        for (Customer c : customers) {
            // create an order
            int numberOfBooks = (int) (Math.random()*10);
            for (int i = 0; i <numberOfBooks; i++) {
                // pick a random book
                int randomBookIndex = (int) (Math.random()*(bookLimit-1));
                Book orderedBook = books.get(randomBookIndex);
                BookAmountPair bookOrder = new BookAmountPair();
                bookOrder.setBookId(orderedBook.getBookId());
                bookOrder.setAmount((int) (Math.random()*3) + 1);
                bookOrders.add(bookOrder);
            }
            CreateOrderRequest request = new CreateOrderRequest();
            request.setItems(bookOrders);
            request.setUsername(c.getUsername());
            try {
                orderService.createOrder(request);
            } catch (RestException ex) {
                System.err.println(ex.buildResponse());
            }

        }
    }
}
