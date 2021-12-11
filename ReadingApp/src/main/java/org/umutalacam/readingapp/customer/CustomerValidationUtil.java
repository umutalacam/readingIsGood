package org.umutalacam.readingapp.customer;

import org.umutalacam.readingapp.customer.exception.CustomerValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerValidationUtil {
    private CustomerValidationUtil() {}

    private final Pattern emailAddressPattern =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    public void validateCustomer(Customer customer) throws CustomerValidationException {
        List<String> errors = new ArrayList<>();
        if (customer.getEmail() == null)
            errors.add("email field is required.");
        else if (!validateEmail(customer.getEmail()))
            errors.add("email is not valid.");

        if (customer.getPassword() == null)
            errors.add("password field is required.");
        else if (customer.getPassword().length() < 6)
            errors.add("password needs to be at least 6 characters.");

        if (customer.getFirstName().isEmpty())
            errors.add("firstName field is required.");

        if (customer.getLastName().isEmpty())
            errors.add("lastName field is required.");

        if (!errors.isEmpty()) {
            throw new CustomerValidationException(errors);
        }
    }

    public static CustomerValidationUtil getInstance() {
        return new CustomerValidationUtil();
    }

    public boolean validateEmail(String emailCandidate) {
        return this.emailAddressPattern.matcher(emailCandidate).matches();
    }

}
