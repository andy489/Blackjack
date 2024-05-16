package com.casino.blackjack.model.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class UserForgotPasswordEvent extends ApplicationEvent {

    private final String email;

    private final String username;

    private final String fullName;

    private final Locale locale;

    public UserForgotPasswordEvent(Object source, String email, String username, String fullName, Locale locale) {
        super(source);
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.locale = locale;
    }
}
