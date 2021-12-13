package org.umutalacam.readingapp.order.request;

import lombok.Data;
import org.umutalacam.readingapp.book.Book;

@Data
public class BookAmountPair {
    private String bookId;
    private Integer amount;
}
