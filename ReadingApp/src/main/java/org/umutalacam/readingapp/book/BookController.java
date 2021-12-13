package org.umutalacam.readingapp.book;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.umutalacam.readingapp.book.response.CreateBookResponse;
import org.umutalacam.readingapp.system.exception.RestException;
import org.umutalacam.readingapp.system.response.PaginatedResponse;
import org.umutalacam.readingapp.system.response.RestResponse;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public PaginatedResponse<Book> getAllBooks(@RequestParam(defaultValue = "0") int p,
                                               @RequestParam(defaultValue = "5", required = false) int pageSize) {
        // Get page of book
        Page<Book> bookPage =  this.bookService.getBookPage(p, pageSize);

        // Build paginated response
        PaginatedResponse<Book> response = new PaginatedResponse<>();
        response.setCurrentPage(bookPage.getNumber());
        response.setPageSize(bookPage.getSize());
        response.setTotalRecords(bookPage.getNumberOfElements());
        response.setRecords(bookPage.getContent());
        response.setTotalPages(bookPage.getTotalPages());
        return response;
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable String id) throws RestException {
        return this.bookService.getBookById(id);
    }

    @PostMapping("/book")
    public ResponseEntity<CreateBookResponse> createBook(@RequestBody Book book) throws RestException {
        String generatedBookId = bookService.createBook(book).getBookId();
        CreateBookResponse response = new CreateBookResponse(generatedBookId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<RestResponse> updateBook(@PathVariable String id, @RequestBody Book book) throws RestException {
        book.setBookId(id);
        this.bookService.updateBook(book);

        RestResponse response = new RestResponse("Book updated successfully.");
        return ResponseEntity.ok(response);
    }

}
