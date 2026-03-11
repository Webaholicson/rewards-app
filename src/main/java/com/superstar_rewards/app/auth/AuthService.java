package com.superstar_rewards.app.auth;

import com.superstar_rewards.app.account.Account;
import com.superstar_rewards.app.account.AccountRepository;
import com.superstar_rewards.app.account.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String API_KEY_HEADER = "X-API-Key";

    private final SessionRepository sessionRepository;
    private final AccountRepository accountRepository;

    /**
     * Resolves the authenticated account from the request using either:
     * - Authorization: Bearer <session-token> (active session)
     * - X-API-Key: <api-key> (account API key)
     */
    public Optional<Account> getAuthenticatedAccount(HttpServletRequest request) {
        Optional<Account> bySession = authenticateBySession(request);
        if (bySession.isPresent()) {
            return bySession;
        }
        return authenticateByApiKey(request);
    }

    private Optional<Account> authenticateBySession(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }
        String token = authHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isEmpty()) {
            return Optional.empty();
        }

        return sessionRepository.findByToken(token)
                .filter(session -> session.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(session -> {
                    sessionRepository.updateLastUsedAt(session.getId(), LocalDateTime.now());
                    return accountRepository.findById(session.getAccountId());
                })
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    private Optional<Account> authenticateByApiKey(HttpServletRequest request) {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (apiKey == null || apiKey.isBlank()) {
            return Optional.empty();
        }
        return accountRepository.findByApiKey(apiKey.trim());
    }
}
