package org.umutalacam.readingapp.order;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.order.request.GetOrdersRequest;
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
    public ResponseEntity<?> createOrder(@RequestBody Order order) throws RestException {
        Order createdOrder = this.orderService.createOrder(order);
        // Build response
        HashMap<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "Order created successfully");
        responseBody.put("orderId", createdOrder.getOrderId());
        return ResponseEntity.ok(responseBody);
    }
}
