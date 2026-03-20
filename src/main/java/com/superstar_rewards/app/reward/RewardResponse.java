package com.superstar_rewards.app.reward;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardResponse {

    private Long id;
    private Long accountId;
    private Integer cost;
    private String title;
    private String description;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;

    public static RewardResponse fromReward(Reward reward) {
        RewardResponse response = new RewardResponse();
        response.setId(reward.getId());
        response.setAccountId(reward.getAccountId());
        response.setCost(reward.getCost());
        response.setTitle(reward.getTitle());
        response.setDescription(reward.getDescription());
        response.setExpiresAt(reward.getExpiresAt());
        response.setCreatedAt(reward.getCreatedAt());
        return response;
    }
}

