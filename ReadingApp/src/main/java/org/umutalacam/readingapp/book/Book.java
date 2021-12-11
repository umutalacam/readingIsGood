package org.umutalacam.readingapp.book;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("book")
@Data
public class Book {
    @Id
    private String bookId;
    private String title;
    private String author;
    private int pressYear;
    private int numPages;
    private int inStock;
    private double price;
}
