package org.umutalacam.readingapp.book;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.book.response.CreateBookResponse;
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
    public Book getBook(@PathVariable String id) throws RestException {
        return this.bookService.getBookById(id);
    }

    @PostMapping("/book")
    public ResponseEntity<CreateBookResponse> createBook(@RequestBody Book book) throws RestException {
        String generatedBookId = bookService.createBook(book);

        if (generatedBookId == null)
            throw new RestException("Error", HttpStatus.BAD_GATEWAY, "/");

        CreateBookResponse response = new CreateBookResponse(generatedBookId);
        return ResponseEntity.ok(response);
    }
}
