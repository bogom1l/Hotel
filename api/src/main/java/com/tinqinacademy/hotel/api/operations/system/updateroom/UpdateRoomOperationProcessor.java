package com.tinqinacademy.hotel.api.operations.system.updateroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class UpdateRoomOperationProcessor implements UpdateRoomOperation{
    @Override
    public Either<ErrorsWrapper, UpdateRoomOutput> process(UpdateRoomInput input) {
        return null;
    }
}
