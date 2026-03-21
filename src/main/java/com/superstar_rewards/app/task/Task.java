package com.superstar_rewards.app.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "reward_amount", nullable = false)
    private Integer rewardAmount;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Task create(
            Long accountId,
            String title,
            String description,
            Integer rewardAmount,
            LocalDateTime now
    ) {
        Task task = new Task();
        task.setAccountId(accountId);
        task.setTitle(title);
        task.setDescription(description);
        task.setRewardAmount(rewardAmount);
        task.setStatus(TaskStatus.PENDING.getValue());
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        return task;
    }
}
