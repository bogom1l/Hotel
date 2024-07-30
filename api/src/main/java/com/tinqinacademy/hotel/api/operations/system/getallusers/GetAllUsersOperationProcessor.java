package com.tinqinacademy.hotel.api.operations.system.getallusers;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class GetAllUsersOperationProcessor implements GetAllUsersOperation{
    @Override
    public Either<ErrorsWrapper, GetAllUsersOutput> process(GetAllUsersInput input) {
        return null;
    }
}
