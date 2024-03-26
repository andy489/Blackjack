package com.casino.blackjack.service;

import com.casino.blackjack.model.entity.UserActivationTokenEntity;
import com.casino.blackjack.model.event.UserRegisteredEvent;
import com.casino.blackjack.repo.UserActivationTokenRepository;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Random;

@Service
public class UserActivationService {

    private static final String ACTIVATION_TOKEN_SYM = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXY0123456789";

    private static final Integer ACTIVATION_TOKEN_LENGTH = 20;

    private final UserService userService;

    private final MailService mailService;

    private final UserActivationTokenRepository userActivationTokenRepository;

    public UserActivationService(UserService userService,
                                 MailService mailService,
                                 UserActivationTokenRepository userActivationTokenRepository) {

        this.userService = userService;
        this.mailService = mailService;
        this.userActivationTokenRepository = userActivationTokenRepository;
    }

    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        String activationToken = createActivationToken(event.getUserEmail());

        mailService.sendRegistrationEmail(event.getUserEmail(), event.getUserFullName(), event.getLocale(), activationToken);
    }

    private String createActivationToken(String userEmail) {

        String token = generateActivationToken();

        UserActivationTokenEntity userActivationTokenEntity = new UserActivationTokenEntity()
                .setActivationToken(token)
                .setUser(userService.findByEmail(userEmail).orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + userEmail + " not found")))
                .setCreateTime(Instant.now());

        userActivationTokenRepository.save(userActivationTokenEntity);

        return token;
    }

    private static String generateActivationToken() {

        StringBuilder activationToken = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_TOKEN_LENGTH; i++) {
            int randInd = random.nextInt(ACTIVATION_TOKEN_SYM.length());

            activationToken.append(ACTIVATION_TOKEN_SYM.charAt(randInd));
        }

        return activationToken.toString();
    }
}
