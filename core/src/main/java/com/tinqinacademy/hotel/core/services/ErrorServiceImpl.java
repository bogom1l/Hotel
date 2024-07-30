package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.error.Error;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.core.services.contracts.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErrorServiceImpl implements ErrorService {

    @Override
    public ErrorsWrapper handleErrors(MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(Error.builder().field(error.getField()).message(error.getDefaultMessage()).build()));

        return ErrorsWrapper.builder().errors(errors).errorCode(HttpStatus.BAD_REQUEST).build();
    }

}
