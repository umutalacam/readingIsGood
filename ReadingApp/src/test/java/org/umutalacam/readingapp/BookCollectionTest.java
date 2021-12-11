package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.umutalacam.readingapp.book.Book;
import org.umutalacam.readingapp.book.BookService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookCollectionTest {
    @Autowired
    private BookService bookService;
    private List<Book> insertedBooks = new ArrayList<>();

    @Test
    public void createBooksTest() {
        Book book = new Book();
        book.setTitle("Hackers: Heroes of the computer revolution.");
        book.setAuthor("Steven Levy");
        book.setInStock(12);
        book.setNumPages(620);
        book.setPressYear(1982);
        book.setPrice(12.98);

        Book book1 = new Book();
        book1.setTitle("Introducton to algorithms");
        book1.setAuthor("MIT Press");
        book1.setInStock(1);
        book1.setNumPages(1200);
        book1.setPressYear(1998);
        book1.setPrice(214.21);

        Book book2 = new Book();
        book2.setTitle("Priestess of The Void");
        book2.setAuthor("John Sculley");
        book2.setInStock(76);
        book2.setNumPages(20);
        book2.setPressYear(1991);
        book2.setPrice(2.0);

        String id = bookService.createBook(book);
        insertedBooks.add(book);
        System.out.println("Created book id: "+id);

        id = bookService.createBook(book1);
        insertedBooks.add(book1);

        System.out.println("Created book id: "+id);

        id = bookService.createBook(book2);
        insertedBooks.add(book2);

        System.out.println("Created book id: "+id);
    }

    @Test
    public void deleteBookTest() {
        for (Book b : insertedBooks) {
            bookService.deleteBookById(b);
        }
    }


}
