package com.superstar_rewards.app.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByToken(String token);

    @Modifying
    @Query("UPDATE Session s SET s.lastUsedAt = :now WHERE s.id = :sessionId")
    void updateLastUsedAt(@Param("sessionId") Long sessionId, @Param("now") LocalDateTime now);
}
