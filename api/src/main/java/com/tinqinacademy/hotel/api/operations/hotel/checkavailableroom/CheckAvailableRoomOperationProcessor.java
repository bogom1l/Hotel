package com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class CheckAvailableRoomOperationProcessor implements CheckAvailableRoomOperation{
    @Override
    public Either<ErrorsWrapper, CheckAvailableRoomOutput> process(CheckAvailableRoomInput input) {
        return null;
    }
}
