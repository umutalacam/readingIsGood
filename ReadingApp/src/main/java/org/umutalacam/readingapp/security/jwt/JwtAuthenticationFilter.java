package org.umutalacam.readingapp.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.umutalacam.readingapp.security.exception.InvalidJwtException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService,
                                   UserDetailsService userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (!validateHeader(authorizationHeader)) {
            // return from the filter
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Create authentication token
        UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean validateHeader(String authorizationHeader) {
        return !(authorizationHeader == null || !authorizationHeader.startsWith("Bearer "));
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        JwtPrincipal jwtPrincipal = null;
        try {
            jwtPrincipal = jwtTokenService.parseToken(token);
            // load user details
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtPrincipal.getUsername());
            return new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
        } catch (InvalidJwtException e) {
            logger.error("Invalid jwt token.");
            return null;
        }
    }
}