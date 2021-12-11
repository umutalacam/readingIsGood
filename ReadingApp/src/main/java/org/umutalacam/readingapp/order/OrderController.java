package org.umutalacam.readingapp.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.HashMap;
import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public List<Order> getOrders() {
        return orderService.getAllOrders();
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
