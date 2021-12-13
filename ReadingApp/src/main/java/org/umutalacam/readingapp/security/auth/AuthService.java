package org.umutalacam.readingapp.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.security.CustomerDetailService;
import org.umutalacam.readingapp.security.CustomerDetails;
import org.umutalacam.readingapp.security.exception.InvalidCredentialsException;
import org.umutalacam.readingapp.security.jwt.JwtTokenService;
import org.umutalacam.readingapp.system.exception.RestException;

@Service
public class AuthService {
    private final CustomerDetailService customerDetailService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(CustomerDetailService customerDetailService,
                       JwtTokenService jwtTokenService) {
        this.customerDetailService = customerDetailService;
        this.jwtTokenService = jwtTokenService;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse login(AuthRequest authRequest) throws RestException {
        // Validate login form
        if (authRequest.getUsername() == null || authRequest.getUsername().isEmpty())
            throw new RestException("username field can't be empty", HttpStatus.BAD_REQUEST);

        if (authRequest.getPassword() == null || authRequest.getPassword().isEmpty())
            throw new RestException("password field can't be empty", HttpStatus.BAD_REQUEST);

        // Trim inputs
        String username = authRequest.getUsername().trim();
        String password = authRequest.getPassword().trim();

        // Perform login operation
        CustomerDetails customerDetails = (CustomerDetails) this.customerDetailService.loadUserByUsername(username);
        Customer customer = customerDetails.getCustomer();

        if (passwordEncoder.matches(password, customer.getPassword())) {
            // login successful create jwt token
            String jwt = this.jwtTokenService.generateJwtForCustomer(customer);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Authentication successful.");
            authResponse.setJwt(jwt);
            return authResponse;
        } else {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
    }
}
