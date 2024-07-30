package com.tinqinacademy.hotel.api.operations.system.deleteroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class DeleteRoomOperationProcessor implements DeleteRoomOperation {

    @Override
    public Either<ErrorsWrapper, DeleteRoomOutput> process(DeleteRoomInput input) {
        return null;
    }
}
