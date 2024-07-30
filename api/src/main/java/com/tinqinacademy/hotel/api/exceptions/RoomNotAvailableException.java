package com.tinqinacademy.hotel.api.exceptions;

public class RoomNotAvailableException extends HotelException {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}