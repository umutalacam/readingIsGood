package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.book.BookService;

@SpringBootTest
public class BookCollectionTest {
    @Autowired
    private BookService bookService;

    @Test
    public void createBookTest() {
        Book book = new Book();
        book.setTitle("Hackers");
        book.setAuthor("Steven Levy");
        book.setInStock(1);
        book.setNumPages(600);
        book.setPressYear(1983);

        bookService.addBook(book);
    }

    public void deleteBookTest() {

    }


}
