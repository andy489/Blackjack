package com.casino.blackjack.service.scheduler;

import com.casino.blackjack.service.auth.UserActivationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ActivationTokenCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationTokenCleaner.class);

    private final UserActivationTokenService userActivationTokenService;

    private final DateTimeFormatter dateTimeFormatter;

    @Value("${auth.activation-token.expires-after-minutes}")
    private Integer minutesBeforeActivationTokenExpires;

    public ActivationTokenCleaner(UserActivationTokenService userActivationTokenService) {
        this.userActivationTokenService = userActivationTokenService;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss");
    }


    // https://crontab.guru/
    @Scheduled(cron = "*/20 * * * * *")
    public void cleanUp() {

        LocalDateTime timeBefore = getTimeBeforeMinutes(minutesBeforeActivationTokenExpires);

        LOGGER.info("Trigger cleanup of activation links at {}.", LocalDateTime.now().format(dateTimeFormatter));

        userActivationTokenService.clearExpiredActivationTokens(timeBefore);
    }

    private LocalDateTime getTimeBeforeMinutes(int minutes) {
        return LocalDateTime.now().minusMinutes(minutes);
    }
}
