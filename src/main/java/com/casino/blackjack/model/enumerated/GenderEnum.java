package com.casino.blackjack.model.enumerated;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("male"),
    FEMALE("female"),
    UNSPECIFIED("Prefer not to say");

    private final String displayName;

    GenderEnum(String displayName) {
        this.displayName = displayName;
    }
}
