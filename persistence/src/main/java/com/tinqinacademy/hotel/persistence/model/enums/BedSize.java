package com.tinqinacademy.hotel.persistence.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BedSize {
    SINGLE("single", 1),
    SMALL_DOUBLE("smallDouble", 2),
    DOUBLE("double", 2),
    KING_SIZE("kingSize", 3),
    QUEEN_SIZE("queensSize", 3),
    UNKNOWN("", 0);

    private final String code;
    private final Integer capacity;

    BedSize(String code, Integer capacity) {
        this.code = code;
        this.capacity = capacity;
    }

    @JsonCreator
    public static BedSize getByCode(String code) {
        for (BedSize bedSize : BedSize.values()) {
            if (code.equals(bedSize.code)) {
                return bedSize;
            }
        }
        return BedSize.UNKNOWN;
    }

    @JsonValue
    public String toString() {
        return this.code;
    }
}
