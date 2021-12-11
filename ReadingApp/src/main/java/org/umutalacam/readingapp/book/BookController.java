package org.umutalacam.readingapp.book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.umutalacam.readingapp.book.exception.BookNotFoundException;
import org.umutalacam.readingapp.system.exception.RestException;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public List<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) throws RestException {
        throw new BookNotFoundException("Book does not exist.", "//");
    }
}
