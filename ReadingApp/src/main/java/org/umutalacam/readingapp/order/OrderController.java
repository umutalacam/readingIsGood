package org.umutalacam.readingapp.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
