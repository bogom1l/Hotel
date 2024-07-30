package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOperation;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetAllUsersOperationProcessor implements GetAllUsersOperation {
    @Override
    public Either<ErrorsWrapper, GetAllUsersOutput> process(GetAllUsersInput input) {
        return null;
    }
}
