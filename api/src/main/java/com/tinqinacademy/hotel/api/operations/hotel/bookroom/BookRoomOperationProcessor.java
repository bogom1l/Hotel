package com.tinqinacademy.hotel.api.operations.hotel.bookroom;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import io.vavr.control.Either;

public class BookRoomOperationProcessor implements CreateRoomOperation {
    @Override
    public Either<ErrorsWrapper, CreateRoomOutput> process(CreateRoomInput input) {
        return null;
    }
}
