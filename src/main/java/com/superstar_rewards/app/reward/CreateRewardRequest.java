package com.superstar_rewards.app.reward;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateRewardRequest {

    @NotNull
    private Integer cost;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private LocalDateTime expiresAt;
}

