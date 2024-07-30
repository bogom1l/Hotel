package com.tinqinacademy.hotel.api.exceptions;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message) {
        //super(String.format("Room with %s is not available", id));
        super(message);
    }
}