package com.corp.esaa.corp.notificationMiddleware._commons.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Filtro que loggea el raw body y los headers de cada request HTTP
 * ANTES de que Spring deserialice el body en un objeto Java.
 */
@Component
public class RequestBodyLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestBodyLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CachingRequestWrapper wrappedRequest = new CachingRequestWrapper(request);

        if (log.isDebugEnabled()) {
            logRequest(wrappedRequest);
        }

        // Continúa la cadena con el wrapper, Spring leerá el body desde el buffer
        filterChain.doFilter(wrappedRequest, response);
    }

    private void logRequest(CachingRequestWrapper request) {
        String headers = Collections.list(request.getHeaderNames())
                .stream()
                .map(name -> name + ": " + request.getHeader(name))
                .collect(Collectors.joining(", "));

        String rawBody = new String(request.getCachedBody(), StandardCharsets.UTF_8);

        log.debug("Incoming request [{} {}] | Headers: [{}] | Body: {}",
                request.getMethod(),
                request.getRequestURI(),
                headers,
                rawBody.isBlank() ? "<empty>" : rawBody);
    }
}
