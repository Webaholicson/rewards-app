package com.superstar_rewards.app.reward;

import com.superstar_rewards.app.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RewardService {

    private final RewardRepository rewardRepository;

    public RewardResponse createReward(Long accountId, CreateRewardRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Reward reward = Reward.create(
                accountId,
                request.getCost(),
                request.getTitle(),
                request.getDescription(),
                request.getExpiresAt(),
                now
        );
        Reward saved = rewardRepository.save(reward);
        return RewardResponse.fromReward(saved);
    }

    public RewardResponse getReward(Long accountId, Long rewardId) {
        Reward reward = rewardRepository.findByIdAndAccountId(rewardId, accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Reward not found"));
        return RewardResponse.fromReward(reward);
    }

    public List<RewardResponse> listRewards(Long accountId) {
        return rewardRepository.findAllByAccountId(accountId)
                .stream()
                .map(RewardResponse::fromReward)
                .toList();
    }
}
