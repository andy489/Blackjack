package com.casino.blackjack.service.scheduler;

import com.casino.blackjack.service.token.UserTokenService;
import com.casino.blackjack.util.LocalDateTimeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ActivationRegistrationObsoleteTokensCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivationRegistrationObsoleteTokensCleaner.class);

    private final UserTokenService userTokenService;

    private final DateTimeFormatter dateTimeFormatter;

    private final LocalDateTimeProvider localDateTimeProvider;

    @Value("${auth.activation-token.expires-after-minutes}")
    private Integer minutesBeforeActivationTokenExpires;

    public ActivationRegistrationObsoleteTokensCleaner(UserTokenService userTokenService, LocalDateTimeProvider localDateTimeProvider) {
        this.userTokenService = userTokenService;
        this.localDateTimeProvider = localDateTimeProvider;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss");
    }

    // https://crontab.guru/
    @Scheduled(cron = "${auth.activation-token.cleanup-cron}")
    public void cleanUp() {

        LocalDateTime timeBefore = localDateTimeProvider.getTimeBeforeMinutes(minutesBeforeActivationTokenExpires);

        LOGGER.info("Trigger cleanup of activation links at {}.", LocalDateTime.now().format(dateTimeFormatter));

        userTokenService.clearExpiredActivationTokens(timeBefore);
    }
}
