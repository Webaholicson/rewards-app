package com.superstar_rewards.app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superstar_rewards.app.account.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Order(1)
@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    public static final String AUTHENTICATED_ACCOUNT_ATTRIBUTE = "authenticatedAccount";

    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isPublicPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Account> account = authService.getAuthenticatedAccount(request);
        if (account.isEmpty()) {
            AuthError error = new AuthError(
                    "You are not authorized to access this resource",
                    HttpServletResponse.SC_UNAUTHORIZED
            );
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(), error);
            return;
        }

        request.setAttribute(AUTHENTICATED_ACCOUNT_ATTRIBUTE, account.get());
        filterChain.doFilter(request, response);
    }

    private boolean isPublicPath(HttpServletRequest request) {
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        String path = request.getRequestURI();
        return "POST".equals(method) && (
                path.endsWith("/account/signup") || path.endsWith("/account/login")
        );
    }
}
