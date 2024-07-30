package com.tinqinacademy.hotel.api.operations.hotel.unbookroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class UnbookRoomOperationProcessor implements UnbookRoomOperation{
    @Override
    public Either<ErrorsWrapper, UnbookRoomOutput> process(UnbookRoomInput input) {
        return null;
    }
}
