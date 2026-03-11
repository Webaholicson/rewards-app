package com.superstar_rewards.app.auth;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public final class AuthError {

    private final String message;
    private final int code;
}
