package com.casino.blackjack.model.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final String email;

    private final String username;

    private final String userFullName;

    private final Locale locale;

    public UserRegisteredEvent(Object source, String email, String username, String userFullName, Locale locale) {
        super(source);
        this.email = email;
        this.username = username;
        this.userFullName = userFullName;
        this.locale = locale;
    }
}
