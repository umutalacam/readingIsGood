package org.umutalacam.readingapp.order;

import lombok.Data;
import org.umutalacam.readingapp.book.Book;

@Data
public class BookOrder {
    private Book book;
    private Integer amount;
}
