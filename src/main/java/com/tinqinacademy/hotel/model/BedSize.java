package com.tinqinacademy.hotel.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BedSize {
    SINGLE("single"),
    SMALL_DOUBLE("smallDouble"),
    DOUBLE("double"),
    KING_SIZE("kingSize"),
    QUEEN_SIZE("queensSize"),
    UNKNOWN("");

    private final String code;

    BedSize(String code) {
        this.code = code;
    }

    @JsonCreator
    public static BedSize getByCode(String code) {
        for (BedSize bedSize : BedSize.values()) {
            if(code.equals(bedSize.code)){
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
