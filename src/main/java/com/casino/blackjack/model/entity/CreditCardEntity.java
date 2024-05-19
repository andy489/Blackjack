package com.casino.blackjack.model.entity;

import jakarta.persistence.Entity;
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



}
