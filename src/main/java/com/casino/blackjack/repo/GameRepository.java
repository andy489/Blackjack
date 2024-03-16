package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Long> {
}
