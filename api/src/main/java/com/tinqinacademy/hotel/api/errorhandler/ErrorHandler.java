package com.tinqinacademy.hotel.api.errorhandler;

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
                Case($(instanceOf(MethodArgumentNotValidException.class)), ex -> handleMethodArgumentNotValidException(ex, errors)),
                Case($(instanceOf(ConstraintViolationException.class)), ex -> handleConstraintViolationException(ex, errors)),
                Case($(instanceOf(RoomNotAvailableException.class)), ex -> handleRoomNotAvailableException(ex, errors)),
                Case($(instanceOf(HotelException.class)), ex -> handleHotelException(ex, errors)),
                Case($(), ex -> handleGenericException(ex, errors))
        );

        return ErrorsWrapper.builder()
                .errors(errors)
                .httpStatus(status)
                .build();
    }

    private HttpStatus handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, List<Error> errors) {
        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.add(Error.builder()
                                .field(error.getField())
                                .message(error.getDefaultMessage())
                                .build()));
        return HttpStatus.BAD_REQUEST;
    }

    private HttpStatus handleConstraintViolationException(ConstraintViolationException ex, List<Error> errors) {
        ex.getConstraintViolations()
                .forEach(violation -> errors.add(Error.builder()
                        .field(violation.getPropertyPath().toString())
                        .message(violation.getMessage())
                        .build()));
        return HttpStatus.BAD_REQUEST;
    }

    private HttpStatus handleRoomNotAvailableException(RoomNotAvailableException ex, List<Error> errors) {
        errors.add(Error.builder().message(ex.getMessage()).build());
        return HttpStatus.FORBIDDEN;
    }

    private HttpStatus handleHotelException(HotelException ex, List<Error> errors) {
        errors.add(Error.builder().message(ex.getMessage()).build());
        return HttpStatus.NOT_FOUND;
    }

    private HttpStatus handleGenericException(Throwable ex, List<Error> errors) {
        errors.add(Error.builder().message(ex.getMessage()).build());
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}