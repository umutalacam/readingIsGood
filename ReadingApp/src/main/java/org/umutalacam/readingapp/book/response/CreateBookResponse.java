package org.umutalacam.readingapp.book.response;

import lombok.Data;
import org.umutalacam.readingapp.system.response.RestResponse;

@Data
public class CreateBookResponse extends RestResponse {
    private String bookId;

    public CreateBookResponse(String bookId) {
        this.bookId = bookId;
        setMessage("Book created successfully.");
    }
}
