package com.superstar_rewards.app.reward;

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
@Table(name = "reward")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static Reward create(
            Long accountId,
            Integer cost,
            String title,
            String description,
            LocalDateTime expiresAt,
            LocalDateTime now
    ) {
        Reward reward = new Reward();
        reward.setAccountId(accountId);
        reward.setCost(cost);
        reward.setTitle(title);
        reward.setDescription(description);
        reward.setExpiresAt(expiresAt);
        reward.setCreatedAt(now);
        return reward;
    }
}

