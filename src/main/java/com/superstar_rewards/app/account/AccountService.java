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

    private static final int SESSION_DAYS = 1;

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
        Account account = Account.fromSignupRequest(
                request,
                passwordEncoder.encode(request.getPassword()),
                generateApiKey(),
                now
        );
        Account saved = accountRepository.save(account);
        return AccountResponse.fromAccountWithApiKey(saved, saved.getApiKey());
    }

    public AccountResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = generateSessionToken();
        LocalDateTime now = LocalDateTime.now();
        Session session = Session.create(account.getId(), token, now, now.plusDays(SESSION_DAYS));
        sessionRepository.save(session);

        return AccountResponse.fromAccountWithSessionToken(account, token);
    }

    private String generateApiKey() {
        return "sk_" + UUID.randomUUID().toString().replace("-", "");
    }

    private String generateSessionToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

