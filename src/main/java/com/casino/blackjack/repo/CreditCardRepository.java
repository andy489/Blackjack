package com.casino.blackjack.repo;

import com.casino.blackjack.model.entity.CreditCardEntity;
import com.casino.blackjack.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCardEntity, Long> {

    List<CreditCardEntity> findByOwner(UserEntity owner);

    Optional<CreditCardEntity> findByCardNumber(String cardNumber);
}
