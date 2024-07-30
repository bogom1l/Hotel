package com.tinqinacademy.hotel.api.base;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public interface OperationProcessor<I extends OperationInput, O extends OperationOutput> {
    Either<ErrorsWrapper, O> process(I input);
}
