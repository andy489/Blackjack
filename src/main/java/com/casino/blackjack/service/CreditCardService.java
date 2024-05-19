package com.casino.blackjack.service;

import com.casino.blackjack.model.dto.CreditCardDTO;
import com.casino.blackjack.model.entity.CreditCardEntity;
import com.casino.blackjack.model.entity.UserEntity;
import com.casino.blackjack.repo.CreditCardRepository;
import com.casino.blackjack.service.auth.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditCardService {

    private final UserService userService;

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(UserService userService, CreditCardRepository creditCardRepository) {
        this.userService = userService;
        this.creditCardRepository = creditCardRepository;
    }


    @Transactional
    public void registerNewCreditCard(CreditCardDTO creditCardDTO, Long currentUserId) {

        Optional<UserEntity> byId = userService.findById(currentUserId);

        if(byId.isEmpty()){
            throw new IllegalStateException("You must be logged in to register credit card");
        }

        UserEntity currentUser = byId.get();

        CreditCardEntity creditCardEntity = new CreditCardEntity()
                .setCardNumber(creditCardDTO.getCardNumber())
                .setCardHolder(creditCardDTO.getCardHolder())
                .setExpiredMonth(creditCardDTO.getExpiredMonth())
                .setExpiredYear(creditCardDTO.getExpiredYear())
                .setCardCvv(creditCardDTO.getCardCvv())
                .setOwner(currentUser);

        CreditCardEntity newlyRegisteredCreditCard = creditCardRepository.save(creditCardEntity);
    }
}
