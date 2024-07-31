package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> handleResult(Either<ErrorsWrapper, ? extends OperationOutput> output) {
        if (output.isRight()) {
            return new ResponseEntity<>(output.get(), HttpStatus.OK);
        }
        ErrorsWrapper errorsWrapper = output.getLeft();
        return new ResponseEntity<>(errorsWrapper.getErrors(), errorsWrapper.getHttpStatus());
    }
}
