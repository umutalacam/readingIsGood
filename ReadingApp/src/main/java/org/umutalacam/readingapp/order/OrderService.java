package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.book.BookService;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.order.exception.InvalidOrderException;
import org.umutalacam.readingapp.order.exception.OrderNotFoundException;
import org.umutalacam.readingapp.order.exception.OutOfStockException;
import org.umutalacam.readingapp.system.exception.RestException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final BookService bookService;


    public OrderService(OrderRepository orderRepository,
                        CustomerService customerService,
                        BookService bookService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    /**
     * Returns all orders in the database
     * @return List of orders
     */
    public List<Order> getOrders() {
        return this.orderRepository.findAll();
    }

    /**
     * Returns a page of orders
     * @param pageIndex Page index
     * @param pageSize elements will be shown in page
     * @return Page of orders
     */
    public Page<Order> getOrdersPage(int pageIndex, int pageSize) {
        return this.orderRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    public Page<Order> getOrdersPageByStatus(OrderStatus status, int pageIndex, int pageSize) {
        return this.orderRepository.findAllByStatus(status, PageRequest.of(pageIndex, pageSize));
    }

    public Order getOrderByOrderId(String orderId) throws RestException {
        Optional<Order> optOrder =  this.orderRepository.findById(orderId);
        if (optOrder.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return optOrder.get();
    }

    public Order createOrder(Order order) throws RestException {
        // todo validation

        // fetch customer for ensuring the customer integrity
        Customer orderCustomer = customerService.getCustomerByUsername(order.getCustomer().getUsername());
        Customer refCustomer = new Customer();
        refCustomer.setCustomerId(orderCustomer.getCustomerId());
        order.setCustomer(refCustomer);

        List<BookOrder> bookOrders = new ArrayList<>();
        double totalPrice = 0;

        // fetch book information for ensuring book data integrity
        for (BookOrder bo: order.getItems()) {
            if (bo.getAmount() <= 0) {
                InvalidOrderException invalidOrderException = new InvalidOrderException();
                invalidOrderException.addErrorMessage("Book order amount must be greater than zero.");
                throw invalidOrderException;
            }
            synchronized (OrderService.class) {
                String orderedBookId = bo.getBook().getBookId();
                Book orderedBook = this.bookService.getBookById(orderedBookId);

                // Check stock status
                int booksInStock = orderedBook.getInStock();
                if (bo.getAmount() > booksInStock) {
                    throw new OutOfStockException();
                }

                // Reduce the books from stock
                this.bookService.removeStockForBook(orderedBook, bo.getAmount());

                // Add to order cost
                totalPrice += Math.abs(orderedBook.getPrice()) * bo.getAmount();

                // Set book orders for saving order embedded
                orderedBook.setInStock(null);
                BookOrder completeOrder = new BookOrder();
                completeOrder.setBook(orderedBook);
                completeOrder.setAmount(bo.getAmount());
                bookOrders.add(completeOrder);
            }
        }

        order.setTotalPrice(totalPrice);
        order.setItems(bookOrders);
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        return this.orderRepository.save(order);
    }

}
