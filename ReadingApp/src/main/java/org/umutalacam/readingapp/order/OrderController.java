package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                                              @RequestParam(required = false) String status) throws RestException {
        Page<Order> orderPage = null;
        if (status == null) {
            // get orders with page
            orderPage = this.orderService.getOrdersPage(p, pageSize);
        }
        else {
            // get orders with status and page
            try {
                OrderStatus orderStatus = OrderStatus.valueOf(status);
                orderPage = this.orderService.getOrdersPageByStatus(orderStatus, p, pageSize);
            } catch (IllegalArgumentException ex) {
                throw new RestException("Invalid argument for status.", HttpStatus.BAD_REQUEST, "/order");
            }
        }

        // Build response
        PaginatedResponse<Order> response = new PaginatedResponse<>();
        response.setCurrentPage(p);
        response.setPageSize(pageSize);
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
    public ResponseEntity<?> createOrder(@RequestBody Order order) throws RestException {
        Order createdOrder = this.orderService.createOrder(order);
        // Build response
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Order created successfully");
        responseBody.put("orderId", createdOrder.getOrderId());
        return ResponseEntity.ok(responseBody);
    }
}
