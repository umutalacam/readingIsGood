package org.umutalacam.readingapp.order.request;

import lombok.Data;

@Data
public class BookAmountPair {
    String bookId;
    int amount;
}
