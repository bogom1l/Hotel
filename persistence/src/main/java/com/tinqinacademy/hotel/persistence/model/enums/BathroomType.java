package com.tinqinacademy.hotel.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BathroomType {
    PRIVATE("private"), SHARED("shared"), UNKNOWN("");

    private final String code;

    BathroomType(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BathroomType getByCode(String code) {
        for (BathroomType bathroomType : BathroomType.values()) {
            if (code.equals(bathroomType.code)) {
                return bathroomType;
            }
        }
        return BathroomType.UNKNOWN;
    }

    @JsonValue
    public String toString() {
        return this.code;
    }

}

