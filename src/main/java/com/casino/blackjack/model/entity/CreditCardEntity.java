package com.casino.blackjack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@Accessors(chain = true)
public class CreditCardEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolder;

    @Column(nullable = false)
    private Integer expiredMonth;

    @Column(nullable = false)
    private Integer expiredYear;

    @Column(nullable = false)
    private Integer cardCvv;

    @OneToOne
    private UserEntity owner;
}
