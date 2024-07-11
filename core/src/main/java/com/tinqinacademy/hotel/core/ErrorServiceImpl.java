package com.tinqinacademy.hotel.core;

//import com.tinqinacademy.hotel.model.error.Error;
//import com.tinqinacademy.hotel.model.error.ErrorWrapper;
//import com.tinqinacademy.hotel.services.contracts.ErrorService;
import com.tinqinacademy.hotel.api.error.ErrorWrapper;
import com.tinqinacademy.hotel.core.contracts.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.tinqinacademy.hotel.api.error.Error;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorWrapper handleErrors(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(
                        Error.builder()
                                .field(error.getField())
                                .message(error.getDefaultMessage())
                                .build()));

        return ErrorWrapper.builder()
                .errors(errors)
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
    }

    // TODO: 1 method that gets generic exceptions (to catch all different exceptions)
    //  receive all different exceptions and return error wrapper?
    //   because not all exceptions have bindings
    //   -- 1 method that handles all different exceptions --
    // constraint validation exception
    // ConstraintViolationException constraintViolationException =


}
