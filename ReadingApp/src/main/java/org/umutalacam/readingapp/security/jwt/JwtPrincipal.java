package org.umutalacam.readingapp.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtPrincipal {
    String customerId;
    String username;
}
