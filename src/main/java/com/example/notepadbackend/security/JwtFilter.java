package com.example.notepadbackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getRequestURI();

        return path.startsWith("/api/auth/login")
                || path.startsWith("/api/auth/create_user");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Step 1: Check token presence
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token missing");
            return;
        }

        String token = authHeader.substring(7);

        try {

            // Step 2: Validate token
            Claims claims = JwtUtil.validateToken(token);

            String username = claims.getSubject();
            Date expiration = claims.getExpiration();

            String responseToken = token;

            // Step 3: Refresh token if expiring soon
            if (JwtUtil.willExpireSoon(expiration)) {
                responseToken = JwtUtil.generateToken(username);
            }

            // Step 4: Authenticate user in Spring Security
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            Collections.emptyList()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Step 5: Continue request execution
            filterChain.doFilter(request, response);

            // Step 6: Attach token in response header
            response.setHeader("Authorization", "Bearer " + responseToken);

        } catch (Exception e) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            e.printStackTrace();
            response.getWriter().write("Invalid token");
        }
    }
}