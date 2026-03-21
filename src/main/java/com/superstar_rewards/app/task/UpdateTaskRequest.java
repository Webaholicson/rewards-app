package com.superstar_rewards.app.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private TaskStatus status;

    @NotNull
    private Integer rewardAmount;
}
