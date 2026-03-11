package com.superstar_rewards.app.account;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public String getAccount(@PathVariable("id") Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/signup")
    public AccountResponse signup(@Valid @RequestBody SignupRequest request) {
        return accountService.signup(request);
    }

    @PostMapping("/login")
    public AccountResponse login(@Valid @RequestBody LoginRequest request) {
        return accountService.login(request);
    }
}

