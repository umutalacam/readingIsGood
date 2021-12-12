package org.umutalacam.readingapp.security.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.security.CustomerDetails;
import org.umutalacam.readingapp.system.exception.RestException;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody AuthRequest request) throws RestException {
        return authService.login(request);
    }

    @GetMapping("/me")
    public Customer me(@AuthenticationPrincipal UserDetails details){
        CustomerDetails customerDetails = (CustomerDetails) details;
        return customerDetails.getCustomer();
    }
}
