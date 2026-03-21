package com.superstar_rewards.app.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByAccountId(Long accountId);

    Optional<Task> findByIdAndAccountId(Long id, Long accountId);

    List<Task> findAllByAccountIdAndStatus(Long accountId, Integer status);
}
