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
public class ForgotPassObsoleteTokensCleaner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPassObsoleteTokensCleaner.class);

    private final UserTokenService userTokenService;

    private final DateTimeFormatter dateTimeFormatter;

    private final LocalDateTimeProvider localDateTimeProvider;

    @Value("${auth.forgot-password-token.expires-after-minutes}")
    private Integer minutesBeforeForgotPassTokenExpires;

    public ForgotPassObsoleteTokensCleaner(UserTokenService userTokenService, LocalDateTimeProvider localDateTimeProvider) {
        this.userTokenService = userTokenService;
        this.localDateTimeProvider = localDateTimeProvider;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss");
    }

    // https://crontab.guru/
    @Scheduled(cron = "${auth.forgot-password-token.cleanup-cron}")
    public void cleanUp() {

        LocalDateTime timeBefore = localDateTimeProvider.getTimeBeforeMinutes(minutesBeforeForgotPassTokenExpires);

        LOGGER.info("Trigger cleanup of forgot pass links at {}.", LocalDateTime.now().format(dateTimeFormatter));

        userTokenService.clearExpiredForgotPassTokens(timeBefore);
    }

}
