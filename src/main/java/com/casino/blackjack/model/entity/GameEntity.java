package com.casino.blackjack.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "games")
@Getter
@Setter
@Accessors(chain = true)
public class GameEntity extends BaseEntity {

    private String playerCards;

    private String dealerCards;

    @OneToOne
    private UserEntity owner;
}
