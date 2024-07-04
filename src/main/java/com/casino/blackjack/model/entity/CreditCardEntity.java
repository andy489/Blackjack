package com.casino.blackjack.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
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
    private Integer cardCvc;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;
}
