package org.umutalacam.readingapp.book;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umutalacam.readingapp.book.exception.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBookById(String bookId) throws BookNotFoundException {
        Optional<Book> optBook = this.bookRepository.findById(bookId);
        if (optBook.isEmpty()) {
            throw new BookNotFoundException("Book with the book id is not found.", null);
        }
        return optBook.get();
    }

    public String createBook(Book book) {
        // validation
        book = bookRepository.save(book);
        return book.getBookId();
    }

    public void deleteBookById(Book book) {
        String bookId = book.getBookId();
        this.bookRepository.deleteById(bookId);
    }

    public void updateBook(Book book) throws BookNotFoundException {
        Optional<Book> optBook = this.bookRepository.findById(book.getBookId());
        if (optBook.isEmpty()) {
            throw new BookNotFoundException("Book with the book id is not found.", null);
        }
        // todo: validation
        Book oldBook = optBook.get();

        // If not set, use old value
        if (book.getTitle() == null) book.setTitle(oldBook.getTitle());
        if (book.getAuthor() == null) book.setAuthor(oldBook.getAuthor());
        if (book.getPressYear() == null) book.setPressYear(oldBook.getPressYear());
        if (book.getNumPages() == null) book.setNumPages(oldBook.getNumPages());
        if (book.getInStock() == null) book.setInStock(oldBook.getInStock());
        if (book.getPrice() == null) book.setPrice(oldBook.getPrice());

        this.bookRepository.save(book);
    }

    @Transactional
    public synchronized void addStockForBook(Book book, int amount) {
        int oldStock = book.getInStock();
        book.setInStock(oldStock + amount);
        this.bookRepository.save(book);
    }

    @Transactional
    public synchronized void removeStockForBook(Book book, int amount) {
        int currentInStock = book.getInStock();
        book.setInStock(currentInStock - amount);
        this.bookRepository.save(book);
    }
}
