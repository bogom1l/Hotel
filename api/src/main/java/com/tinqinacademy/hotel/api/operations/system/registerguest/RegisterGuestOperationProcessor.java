package com.tinqinacademy.hotel.api.operations.system.registerguest;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class RegisterGuestOperationProcessor implements RegisterGuestOperation{
    @Override
    public Either<ErrorsWrapper, RegisterGuestOutput> process(RegisterGuestInput input) {
        return null;
    }
}
