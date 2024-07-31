package com.tinqinacademy.hotel.api.error;

import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Component
public class ErrorHandler {

    public ErrorsWrapper handleErrors(Throwable throwable) {
        List<Error> errors = new ArrayList<>();

        HttpStatus status = Match(throwable).of(

                Case($(instanceOf(MethodArgumentNotValidException.class)), ex -> {
                    ex.getBindingResult().getFieldErrors()
                            .forEach(error ->
                                    errors.add(Error.builder()
                                            .field(error.getField())
                                            .message(error.getDefaultMessage())
                                            .build()));
                    return HttpStatus.BAD_REQUEST;
                }),

                Case($(instanceOf(ConstraintViolationException.class)), ex -> {
                    ex.getConstraintViolations()
                            .forEach(violation -> errors.add(Error.builder()
                                    .field(violation.getPropertyPath().toString())
                                    .message(violation.getMessage())
                                    .build()));
                    return HttpStatus.BAD_REQUEST;
                }),

                Case($(instanceOf(RoomNotAvailableException.class)), ex -> {
                    errors.add(Error.builder().message(ex.getMessage()).build());
                    return HttpStatus.FORBIDDEN;
                }),

                Case($(instanceOf(HotelException.class)), ex -> {
                    errors.add(Error.builder().message(ex.getMessage()).build());
                    return HttpStatus.NOT_FOUND;
                }),

                Case($(), ex -> {
                    errors.add(Error.builder().message(ex.getMessage()).build());
                    return HttpStatus.INTERNAL_SERVER_ERROR;
                })
        );

        return ErrorsWrapper.builder()
                .errors(errors)
                .httpStatus(status)
                .build();
    }




}
