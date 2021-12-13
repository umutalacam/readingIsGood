package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.order.request.CreateOrderRequest;
import org.umutalacam.readingapp.order.request.GetOrdersRequest;
import org.umutalacam.readingapp.order.response.OrderCreatedResponse;
import org.umutalacam.readingapp.security.CustomerDetails;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.response.PaginatedResponse;

import java.util.HashMap;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public PaginatedResponse<Order> getOrders(@RequestParam(defaultValue = "0") int p,
                                              @RequestParam(defaultValue = "5") int pageSize,
                                              @RequestBody GetOrdersRequest request) throws RestException {
        if (request.getPageSize() == null) {
            request.setPageSize(pageSize);
        }

        if (request.getPageIndex() == null) {
            request.setPageIndex(p);
        }

        // Use get orders request object
        Page<Order> orderPage = this.orderService.getOrdersPage(request);

        // Build response
        PaginatedResponse<Order> response = new PaginatedResponse<>();
        response.setCurrentPage(request.getPageIndex());
        response.setPageSize(request.getPageSize());
        response.setTotalPages(orderPage.getTotalPages());
        response.setTotalRecords(orderPage.getTotalElements());
        response.setRecords(orderPage.getContent());
        return response;
    }

    @GetMapping("/order/{orderId}")
    public Order getOrder(@PathVariable String orderId) throws RestException {
        return this.orderService.getOrderByOrderId(orderId);
    }

    @PostMapping("/order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest orderRequest,
                                         @AuthenticationPrincipal CustomerDetails details) throws RestException {
        Customer currentCustomer = details.getCustomer();
        orderRequest.setUsername(currentCustomer.getUsername());

        Order createdOrder = this.orderService.createOrder(orderRequest);
        // Build response
        OrderCreatedResponse response = new OrderCreatedResponse();
        response.setOrderId(createdOrder.getOrderId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customer/orders")
    public PaginatedResponse<Order> getCustomerOrders(@RequestParam(defaultValue = "0") int p,
                                                      @RequestParam(defaultValue = "5") int pageSize,
                                                      @RequestParam() String customer) throws RestException {
        Page<Order> orderPage =  this.orderService.getOrdersPageByCustomer(customer, p, pageSize);
        PaginatedResponse<Order> orderPaginatedResponse = new PaginatedResponse<>();
        orderPaginatedResponse.setCurrentPage(p);
        orderPaginatedResponse.setTotalPages(orderPage.getTotalPages());
        orderPaginatedResponse.setPageSize(pageSize);
        orderPaginatedResponse.setTotalRecords(orderPage.getTotalElements());
        orderPaginatedResponse.setRecords(orderPage.getContent());
        return orderPaginatedResponse;
    }
}
