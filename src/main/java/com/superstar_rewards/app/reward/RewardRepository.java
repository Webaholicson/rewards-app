package com.superstar_rewards.app.reward;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {

    List<Reward> findAllByAccountId(Long accountId);

    Optional<Reward> findByIdAndAccountId(Long id, Long accountId);
}

