package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.UserActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;
import java.util.Optional;

public interface UserActivationTokenRepository extends JpaRepository<UserActivationTokenEntity, Long> {
    void deleteByUserId(Long userId);

    Optional<UserActivationTokenEntity> findByToken(String token);

    @Modifying
    void deleteByCreatedAtBefore(Instant createdAt);
}
