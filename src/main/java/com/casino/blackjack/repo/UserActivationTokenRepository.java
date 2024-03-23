package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.UserActivationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActivationTokenRepository extends JpaRepository<UserActivationTokenEntity, Long> {
}
