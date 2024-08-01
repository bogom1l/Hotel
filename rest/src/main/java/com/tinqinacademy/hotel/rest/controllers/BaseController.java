package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.errorhandler.ErrorsWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected ResponseEntity<?> handle(Either<ErrorsWrapper, ? extends OperationOutput> output) {
        if (output.isLeft()) {
            return error(output);
        }
        return new ResponseEntity<>(output.get(), HttpStatus.OK); //todo ternar operator
    }

    protected ResponseEntity<?> handleWithStatus(Either<ErrorsWrapper, ? extends OperationOutput> output, HttpStatus status) {
        if (output.isLeft()) {
            return error(output);
        }
        return new ResponseEntity<>(output.get(), status);
    }

    private ResponseEntity<?> error(Either<ErrorsWrapper, ? extends OperationOutput> output) {
        ErrorsWrapper errorWrapper = output.getLeft();
        return new ResponseEntity<>(errorWrapper.getErrors(), errorWrapper.getHttpStatus());
    }
}
