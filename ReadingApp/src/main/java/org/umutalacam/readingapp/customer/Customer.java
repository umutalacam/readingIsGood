package org.umutalacam.readingapp.customer;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("customer")
public class Customer {
    private String name;
    private String email;
    private String encodedPassword;
}
