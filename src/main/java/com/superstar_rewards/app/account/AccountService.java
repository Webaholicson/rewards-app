package com.superstar_rewards.app.account;

import com.superstar_rewards.app.exception.EmailAlreadyInUseException;
import com.superstar_rewards.app.exception.InvalidCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {

    private static final int SESSION_DAYS = 7;

    private final AccountRepository accountRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    public String getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(account -> "Account details for id " + account.getId())
                .orElse("Account not found for id " + accountId);
    }

    public AccountResponse signup(SignupRequest request) {
        accountRepository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    throw new EmailAlreadyInUseException();
                });

        LocalDateTime now = LocalDateTime.now();

        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setApiKey(generateApiKey());
        account.setCreatedAt(now);
        account.setUpdatedAt(now);
        account.setIsAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : false);
        account.setIsVerified(false);

        Account saved = accountRepository.save(account);
        AccountResponse response = toResponse(saved);
        response.setApiKey(saved.getApiKey());
        return response;
    }

    public AccountResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = generateSessionToken();
        LocalDateTime now = LocalDateTime.now();
        Session session = new Session();
        session.setAccountId(account.getId());
        session.setToken(token);
        session.setCreatedAt(now);
        session.setExpiresAt(now.plusDays(SESSION_DAYS));
        sessionRepository.save(session);

        AccountResponse response = toResponse(account);
        response.setSessionToken(token);
        return response;
    }

    private String generateApiKey() {
        return "sk_" + UUID.randomUUID().toString().replace("-", "");
    }

    private String generateSessionToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private AccountResponse toResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setId(account.getId());
        response.setFirstName(account.getFirstName());
        response.setLastName(account.getLastName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setCreatedAt(account.getCreatedAt());
        response.setUpdatedAt(account.getUpdatedAt());
        response.setIsAdmin(account.getIsAdmin());
        response.setIsVerified(account.getIsVerified());
        return response;
    }
}

