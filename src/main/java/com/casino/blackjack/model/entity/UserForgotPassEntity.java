package com.casino.blackjack.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "reset_pass_tokens")
@Getter
@Setter
@Accessors(chain = true)
public class UserForgotPassEntity extends BaseEntity{

    @OneToOne
    private UserEntity user;

    private String token;

    private Instant createdAt;
}
