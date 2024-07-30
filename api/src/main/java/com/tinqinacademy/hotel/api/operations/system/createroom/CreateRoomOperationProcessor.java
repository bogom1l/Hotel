package com.tinqinacademy.hotel.api.operations.system.createroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class CreateRoomOperationProcessor implements CreateRoomOperation {
    @Override
    public Either<ErrorsWrapper, CreateRoomOutput> process(CreateRoomInput input) {
        return null;
    }
}
