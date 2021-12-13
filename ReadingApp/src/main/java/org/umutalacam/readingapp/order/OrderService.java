package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.book.BookService;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.customer.CustomerService;
import org.umutalacam.readingapp.order.exception.IllegalOrdersRequest;
import org.umutalacam.readingapp.order.exception.InvalidOrderException;
import org.umutalacam.readingapp.order.exception.OrderNotFoundException;
import org.umutalacam.readingapp.order.exception.OutOfStockException;
import org.umutalacam.readingapp.order.request.BookAmountPair;
import org.umutalacam.readingapp.order.request.CreateOrderRequest;
import org.umutalacam.readingapp.order.request.GetOrdersRequest;
import org.umutalacam.readingapp.system.exception.RestException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final BookService bookService;
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());


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
        logger.info("Get all orders.");
        return this.orderRepository.findAll();
    }

    /**
     * Returns the orders page with the defined criteria in request
     * @param request request
     * @return page of orders
     */
    public Page<Order> getOrdersPage(GetOrdersRequest request) throws RestException {
        if (request.getPageIndex() < 0) {
            throw new IllegalOrdersRequest("Page index cannot be smaller than 0.");
        }
        if (request.getPageSize() <= 0) {
            throw new IllegalOrdersRequest("Page size cannot be smaller than 1.");
        }
        PageRequest pageRequest = PageRequest.of(request.getPageIndex(), request.getPageSize());
        int pageIndex = request.getPageIndex();
        int pageSize = request.getPageSize();
        Date startDate;
        Date endDate;
        logger.info("Retrieving order pages between dates. " +
                "pageIndex:" + request.getPageIndex() + " pageSize:" + request.getPageSize());
        logger.info("");
        try {
            startDate = request.getStartDate();
            endDate = request.getEndDate();
        } catch (ParseException exception) {
            throw new RestException("Date fields are malformed. Allowed format: dd-MM-yyyy", HttpStatus.BAD_REQUEST, null);
        }

        if (startDate == null && endDate == null) {
            logger.info("No date interval is given. Fethcing all orders.");
            return this.orderRepository.findAll(PageRequest.of(pageIndex, pageSize));
        }
        else {
            if (startDate == null) {
                startDate = new Date(0);
            }
            if (endDate == null) {
                endDate = new Date(Long.MAX_VALUE);
            }
            logger.info("Fetching orders between " + startDate + " and" + endDate);
            return this.orderRepository.findAllByOrderTimeBetween(startDate, endDate, pageRequest);
        }
    }

    /**
     * Retrieves orders according to their status
     * @param status OrderStatus
     * @param pageIndex index of page
     * @param pageSize items in page
     * @return page of orders
     */
    public Page<Order> getOrdersPageByStatus(OrderStatus status, int pageIndex, int pageSize) {
        logger.info("Retrieving order page by status:" + status);
        return this.orderRepository.findAllByStatus(status, PageRequest.of(pageIndex, pageSize));
    }

    /**
     * Retrieves orders of customer
     * @param username customer user name
     * @param pageSize items in page
     * @return page of orders
     * @throws RestException thrown if an error occurred
     */
    public Page<Order> getOrdersPageByCustomer(String username, int pageIndex, int pageSize) throws RestException {
        Customer customer = this.customerService.getCustomerByUsername(username);
        logger.info("Getting orders of customer: " + customer);
        return this.orderRepository.findAllByCustomer(customer, PageRequest.of(pageIndex, pageSize));
    }

    /**
     * Retrieves an order by order id
     * @param orderId id of the order
     * @return order
     * @throws RestException thrown if an error occurred while retrieving order
     */
    public Order getOrderByOrderId(String orderId) throws RestException {
        logger.info("Getting order with id:"+ orderId);
        Optional<Order> optOrder =  this.orderRepository.findById(orderId);
        if (optOrder.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return optOrder.get();
    }

    /**
     * Creates and persists order.
     * @param orderRequest
     * @return
     * @throws RestException
     */
    public Order createOrder(CreateOrderRequest orderRequest) throws RestException {
        // Create an order object which will be persisted in db
        Order order = new Order();

        // Fetch customer for ensuring the customer integrity
        Customer orderCustomer = customerService.getCustomerByUsername(orderRequest.getUsername());
        order.setCustomer(orderCustomer);
        logger.info("Creating order for customer, " + orderCustomer.getUsername());

        // Create book orders list
        List<BookOrder> bookOrders = new ArrayList<>();
        double totalPrice = 0;

        // Fetch each ordered book information for ensuring book data integrity
        for (BookAmountPair bo: orderRequest.getItems()) {
            logger.info("Fetching details with book amount pair. " + bo);
            // check if requested book amount is legal
            if (bo.getAmount() <= 0) {
                InvalidOrderException invalidOrderException = new InvalidOrderException();
                invalidOrderException.addErrorMessage("Book order amount must be greater than zero.");
                throw invalidOrderException;
            }
            // Critical zone for checking stocks
            synchronized (OrderService.class) {
                // Fetch ordered book
                String orderedBookId = bo.getBookId();
                Book orderedBook = this.bookService.getBookById(orderedBookId);
                logger.info("Fetched ordered book: " + orderedBook);

                // Check stock status
                int booksInStock = orderedBook.getInStock();
                if (bo.getAmount() > booksInStock) {
                    throw new OutOfStockException();
                }

                // Reduce the books from stock
                logger.info("Updating stock of book: " + orderedBook);
                this.bookService.removeStockForBook(orderedBook, bo.getAmount());

                // Add price to total order cost
                totalPrice += Math.abs(orderedBook.getPrice()) * bo.getAmount();

                // Set book orders for saving order embedded
                orderedBook.setInStock(null);
                BookOrder completeOrder = new BookOrder();
                completeOrder.setBook(orderedBook);
                completeOrder.setAmount(bo.getAmount());
                bookOrders.add(completeOrder);
            }
        }

        // Set order fields
        order.setTotalPrice(totalPrice);
        order.setItems(bookOrders);
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        Order persistedOrder  =  this.orderRepository.save(order);
        logger.info("Order saved successfully. orderId:" + persistedOrder.getOrderId());
        return persistedOrder;
    }

}
