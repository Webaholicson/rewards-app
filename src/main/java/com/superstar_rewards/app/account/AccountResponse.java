package com.superstar_rewards.app.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isAdmin;
    private Boolean isVerified;
    private String sessionToken;
    private String apiKey;

    public static AccountResponse fromAccount(Account account) {
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

    public static AccountResponse fromAccountWithApiKey(Account account, String apiKey) {
        AccountResponse response = fromAccount(account);
        response.setApiKey(apiKey);
        return response;
    }

    public static AccountResponse fromAccountWithSessionToken(Account account, String sessionToken) {
        AccountResponse response = fromAccount(account);
        response.setSessionToken(sessionToken);
        return response;
    }
}

