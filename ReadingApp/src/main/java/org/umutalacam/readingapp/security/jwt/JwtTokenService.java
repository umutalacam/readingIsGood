package org.umutalacam.readingapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.customer.Customer;
import org.umutalacam.readingapp.security.exception.InvalidJwtException;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class JwtTokenService {
    private static final Logger logger = Logger.getLogger(JwtTokenService.class.getName());
    private final Key JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Generates a token for customer
     * @param customer customer
     * @return jwt token string
     */
    public String generateJwtForCustomer(Customer customer) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        String compactTokenString = Jwts.builder()
                .setSubject(customer.getUsername())
                .claim("customerId", customer.getCustomerId())
                .setExpiration(expirationDate)
                .signWith(JWT_SECRET, SignatureAlgorithm.HS256)
                .compact();

        return compactTokenString;
    }

    /**
     * Parse JWT token and extract jwt principal
     * @param token jwt token
     * @return jwt principal that holds customer id and username
     */
    public JwtPrincipal parseToken(String token) throws InvalidJwtException {
        Jws<Claims> jwsClaims;
        try {
            jwsClaims = Jwts.parserBuilder()
                .setSigningKey(JWT_SECRET)
                .build()
                .parseClaimsJws(token);
        } catch (Exception ex) {
            logger.severe("Exception while extracting claims from jwt: "+ ex.getMessage());
            throw new InvalidJwtException();
        }

        String customerId = jwsClaims.getBody().get("customerId", String.class);
        String username = jwsClaims.getBody().getSubject();

        return new JwtPrincipal(customerId, username);
    }

}
