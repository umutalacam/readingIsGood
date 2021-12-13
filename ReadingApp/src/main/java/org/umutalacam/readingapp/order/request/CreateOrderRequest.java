package org.umutalacam.readingapp.order.request;

import lombok.Data;
import org.umutalacam.readingapp.customer.Customer;

import java.util.List;

@Data
public class CreateOrderRequest {
    private String username;
    private List<BookAmountPair> items;
}
