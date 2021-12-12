package org.umutalacam.readingapp.order.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    List<BookAmountPair> items;
}
