package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.CreditCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {
}
