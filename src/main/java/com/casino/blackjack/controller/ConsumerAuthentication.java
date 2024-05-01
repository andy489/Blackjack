package com.casino.blackjack.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface ConsumerAuthentication {

    void accept(Authentication authentication, HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse);

}
