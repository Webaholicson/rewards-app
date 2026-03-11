package com.superstar_rewards.app.account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "api_key")
    private String apiKey;

    public static Account fromSignupRequest(SignupRequest request, String encodedPassword, String apiKey, LocalDateTime now) {
        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setPassword(encodedPassword);
        account.setApiKey(apiKey);
        account.setCreatedAt(now);
        account.setUpdatedAt(now);
        account.setIsAdmin(request.getIsAdmin() != null ? request.getIsAdmin() : false);
        account.setIsVerified(false);
        return account;
    }
}

