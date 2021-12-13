package org.umutalacam.readingapp.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.umutalacam.readingapp.book.exception.BookNotFoundException;
import org.umutalacam.readingapp.book.exception.BookValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Returns all book elements
     * @return list of books
     */
    public List<Book> getAllBooks() {
        logger.info("Retrieving all books.");
        return this.bookRepository.findAll();
    }

    /**
     * Returns a page of book elements
     * @param pageIndex page index
     * @param pageSize elements per page
     * @return page of books
     */
    public Page<Book> getBookPage(int pageIndex, int pageSize) {
        logger.info("Retrieving page of books. pageIndex:"+pageIndex+" pageSize:"+pageSize);
        return this.bookRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    /**
     * Returns the book with the given bookId
     * @param bookId bookId to search
     * @return found book object
     * @throws BookNotFoundException thrown if book does not exist in the database
     */
    public Book getBookById(String bookId) throws BookNotFoundException {
        Optional<Book> optBook = this.bookRepository.findById(bookId);
        if (optBook.isEmpty()) {
            throw new BookNotFoundException("Book with the book id is not found.", null);
        }
        return optBook.get();
    }

    /**
     * Creates a book object and persists in the database
     * @param book new book object to be saved
     * @return
     * @throws BookValidationException
     */
    public Book createBook(Book book) throws BookValidationException {
        // Validate the book data
        logger.info("Creating book: " + book.toString());
        logger.info("Validating book record.");
        BookValidationUtil.getInstance().validateBook(book);
        book = bookRepository.save(book);
        logger.info("Book saved successfully.");
        return book;
    }

    /**
     * Deletes book with the id of the given book
     * @param book Book object to be deleted.
     */
    public void deleteBookById(Book book) {
        String bookId = book.getBookId();
        logger.info("Deleting book with the id: " + bookId);
        this.bookRepository.deleteById(bookId);
        logger.info("Book deleted successfully.");
    }

    /**
     * Updates book fields
     * @param book book object that updated fields are set.
     * @throws BookNotFoundException thrown if the book does not exist
     */
    public void updateBook(Book book) throws BookNotFoundException {
        logger.info("Retrieving existing book entity.");
        Optional<Book> optBook = this.bookRepository.findById(book.getBookId());
        if (optBook.isEmpty()) {
            logger.warning("Book does not exist in database.");
            throw new BookNotFoundException("Book with the book id is not found.", null);
        }
        Book oldBook = optBook.get();
        logger.info("Updating book fields: " + Arrays.toString(book.getClass().getDeclaredFields()));
        // If not set, use old value
        if (book.getTitle() == null) book.setTitle(oldBook.getTitle());
        if (book.getAuthor() == null) book.setAuthor(oldBook.getAuthor());
        if (book.getPressYear() == null) book.setPressYear(oldBook.getPressYear());
        if (book.getNumPages() == null) book.setNumPages(oldBook.getNumPages());
        if (book.getInStock() == null) book.setInStock(oldBook.getInStock());
        if (book.getPrice() == null) book.setPrice(oldBook.getPrice());

        this.bookRepository.save(book);
        logger.info("Book updated successfully.");
    }

    /**
     * Increase the stock of a book in a particular amount
     * @param book book
     * @param amount amount
     */
    @Transactional
    public synchronized void addStockForBook(Book book, int amount) {
        int oldStock = book.getInStock();
        book.setInStock(oldStock + amount);
        this.bookRepository.save(book);
    }

    /**
     * Decrease the stock of a book in a particular amount
     * @param book book
     * @param amount amount
     */
    @Transactional
    public synchronized void removeStockForBook(Book book, int amount) {
        logger.info("Decreasing stock count for book: " + book);
        int currentInStock = book.getInStock();
        book.setInStock(currentInStock - amount);
        this.bookRepository.save(book);
        logger.info("Updated stock number successfully.");
    }
}
