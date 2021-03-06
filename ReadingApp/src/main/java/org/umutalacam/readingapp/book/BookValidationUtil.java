package org.umutalacam.readingapp.book;

import org.umutalacam.readingapp.book.exception.BookValidationException;

import java.util.ArrayList;
import java.util.List;

public class BookValidationUtil {
    private BookValidationUtil() {}

    /**
     * Validates that book object has required fields for persisting
     * @param book book to be validated
     * @throws BookValidationException thrown if book has missing fields or not valid.
     */
    public void validateBook(Book book) throws BookValidationException {
        // All fields are required for book
        List<String> errors = new ArrayList<>();
        if (book.getInStock() == null)
            errors.add("inStock field is required.");
        else if (book.getInStock() < 0)
            errors.add("inStock field needs to be a positive integer");

        if (book.getPrice() == null)
            errors.add("price field is required.");
        if (book.getTitle() == null)
            errors.add("title field is required.");

        if (!errors.isEmpty())
            throw new BookValidationException("Book is not valid.", errors);

    }

    public static BookValidationUtil getInstance() {
        return new BookValidationUtil();
    }
}
