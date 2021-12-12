package org.umutalacam.readingapp.security.auth;

import lombok.Data;

@Data
public class AuthResponse {
    String message;
    String jwt;
}
