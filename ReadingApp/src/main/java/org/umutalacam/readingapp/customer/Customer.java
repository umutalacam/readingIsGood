package org.umutalacam.readingapp.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("customer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {
    @Id
    @JsonIgnore
    private String customerId;

    @Indexed(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
