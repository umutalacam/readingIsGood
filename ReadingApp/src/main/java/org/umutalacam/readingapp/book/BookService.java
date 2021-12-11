package org.umutalacam.readingapp.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public void addBook(Book book) {
        // validation
        this.bookRepository.save(book);
    }

    public void deleteBook(Book book) {
        this.bookRepository.deleteById(book.getId());
    }

    public void addStockForBook(Book book) {

    }

    public void removeStockForBook(Book book) {

    }
}
