package com.superstar_rewards.app.task;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

    private Long id;
    private Long accountId;
    private String title;
    private String description;
    private Integer rewardAmount;
    private TaskStatus status;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TaskResponse fromTask(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setAccountId(task.getAccountId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setRewardAmount(task.getRewardAmount());
        response.setStatus(TaskStatus.fromValue(task.getStatus()));
        response.setCompletedAt(task.getCompletedAt());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}
