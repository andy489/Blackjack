package com.casino.blackjack.model.enumerated;


import lombok.Getter;

@Getter
public enum LocaleEnum {
    EN("en_US", "English"),
    BG("bg_BG", "Български");

    private final String baseLocaleId;
    private final String displayName;

    LocaleEnum(String baseLocaleId, String displayName) {
        this.baseLocaleId = baseLocaleId;
        this.displayName = displayName;
    }
}
