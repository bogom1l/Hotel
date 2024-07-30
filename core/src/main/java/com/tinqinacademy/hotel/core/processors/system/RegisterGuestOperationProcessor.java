package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOperation;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterGuestOperationProcessor implements RegisterGuestOperation {
    @Override
    public Either<ErrorsWrapper, RegisterGuestOutput> process(RegisterGuestInput input) {
        return null;
    }
}
