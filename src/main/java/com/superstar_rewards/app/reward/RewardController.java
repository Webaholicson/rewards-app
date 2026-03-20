package com.superstar_rewards.app.reward;

import com.superstar_rewards.app.account.Account;
import com.superstar_rewards.app.auth.AuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reward")
@AllArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @PostMapping
    public RewardResponse createReward(HttpServletRequest request, @Valid @RequestBody CreateRewardRequest body) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return rewardService.createReward(account.getId(), body);
    }

    @GetMapping("/{id}")
    public RewardResponse getReward(HttpServletRequest request, @PathVariable("id") Long id) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return rewardService.getReward(account.getId(), id);
    }

    @GetMapping("/me")
    public List<RewardResponse> listMyRewards(HttpServletRequest request) {
        Account account = (Account) request.getAttribute(AuthFilter.AUTHENTICATED_ACCOUNT_ATTRIBUTE);
        return rewardService.listRewards(account.getId());
    }
}

