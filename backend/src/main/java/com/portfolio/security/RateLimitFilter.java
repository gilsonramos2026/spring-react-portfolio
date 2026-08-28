package com.portfolio.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate limiting simples em memória para o endpoint público de contato.
 * Permite no máximo MAX_REQUESTS requisições por IP em WINDOW_SECONDS.
 */
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private static final int    MAX_REQUESTS    = 5;
    private static final long   WINDOW_SECONDS  = 60L;
    private static final String CONTACT_PATH    = "/public/contact";

    private final Map<String, long[]> store = new ConcurrentHashMap<>();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().endsWith(CONTACT_PATH)
                || !"POST".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String ip = resolveClientIp(request);
        long now = Instant.now().getEpochSecond();

        store.merge(ip, new long[]{now, 1}, (existing, newVal) -> {
            if (now - existing[0] > WINDOW_SECONDS) return new long[]{now, 1};
            existing[1]++;
            return existing;
        });

        long[] entry = store.get(ip);
        if (entry[1] > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    "{\"status\":429,\"error\":\"Too Many Requests\","
                            + "\"message\":\"Limite de envios atingido. Aguarde 1 minuto.\"}"
            );
            return;
        }

        if (store.size() > 500) {
            store.entrySet().removeIf(e -> now - e.getValue()[0] > WINDOW_SECONDS * 10);
        }

        chain.doFilter(request, response);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
