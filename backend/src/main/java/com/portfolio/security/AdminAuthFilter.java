package com.portfolio.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AdminAuthFilter extends OncePerRequestFilter {

    @Value("${app.admin.secret-key}")
    private String secret;

    private final SecurityErrorResolver errorResolver; // Injeção do componente de erro

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return "OPTIONS".equalsIgnoreCase(req.getMethod()) || !req.getRequestURI().contains("/admin/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String key = req.getHeader("X-Admin-Key");

        if (key == null || !key.equals(secret)) {
            errorResolver.handleUnauthorized(res, "Chave admin inválida");
            return;
        }

        chain.doFilter(req, res);
    }
}
