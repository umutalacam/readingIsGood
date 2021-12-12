package org.umutalacam.readingapp.order.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    List<BookAmountPair> items;
}
