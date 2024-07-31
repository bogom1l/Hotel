package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<?> handle(Either<ErrorsWrapper, ? extends OperationOutput> output) {
        if(output.isLeft()){
            return error(output);
        }
        return new ResponseEntity<>(output.get(), HttpStatus.OK);
    }

    protected ResponseEntity<?> handleWithStatus(Either<ErrorsWrapper, ? extends OperationOutput> output, HttpStatus status) {
        if(output.isLeft()){
            return error(output);
        }
        return new ResponseEntity<>(output.get(), status);
    }

    private ResponseEntity<?> error(Either<ErrorsWrapper, ? extends OperationOutput> output){
        ErrorsWrapper errorWrapper = output.getLeft();
        return new ResponseEntity<>(errorWrapper.getErrors(), errorWrapper.getHttpStatus());
    }

    /*

        protected<O extends OperationOutput> ResponseEntity<?> handleWithCode(Either<ErrorWrapper,O> result, HttpStatus status){
        if (result.isRight()) {
            O output = result.get();
            return new ResponseEntity<>(output, status);
        } else {
            return error(result);
        }
    }

    protected<O extends OperationOutput> ResponseEntity<?> handle(Either<ErrorWrapper,O> result){
        if (result.isRight()) {
            O output = result.get();
            return new ResponseEntity<>(output, HttpStatus.OK);
        } else {
            return error(result);
        }
    }

    private<O extends OperationOutput> ResponseEntity<?> error(Either<ErrorWrapper,O> result){
        ErrorWrapper errorWrapper = result.getLeft();
        return new ResponseEntity<>(errorWrapper.getErrors(), errorWrapper.getErrorCode());
    }
     */
}
