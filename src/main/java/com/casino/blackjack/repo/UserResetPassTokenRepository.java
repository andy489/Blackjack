package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.UserActivationTokenEntity;
import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.model.entity.UserForgotPassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;
import java.util.Optional;

public interface UserResetPassTokenRepository extends JpaRepository<UserForgotPassEntity, Long> {


    Optional<UserForgotPassEntity> findByToken(String token);

    void deleteByCreatedAtBefore(Instant createdAt);

    Long countByUserId(Long id);

    Long deleteByUserId(Long id);

    Optional<UserForgotPassEntity> findByUserId(Long id);
}
