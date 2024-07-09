package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.error.Error;
import com.tinqinacademy.hotel.model.error.ErrorWrapper;
import com.tinqinacademy.hotel.services.contracts.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

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
}
