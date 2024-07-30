package com.tinqinacademy.hotel.api.error;

import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Component
public class ErrorHandler {

    public ErrorsWrapper handleErrors_v2(Throwable throwable) {
        List<Error> errors = new ArrayList<>();
        HttpStatus status;

        switch (throwable) {
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                methodArgumentNotValidException.getBindingResult().getFieldErrors()
                        .forEach(error -> errors.add(Error
                                .builder()
                                .field(error.getField())
                                .message(error.getDefaultMessage())
                                .build()));
                status = HttpStatus.BAD_REQUEST;
            }
            case RoomNotAvailableException roomNotAvailableException -> {
                errors.add(Error.builder().message(throwable.getMessage()).build());
                status = HttpStatus.CONFLICT;
            }
            case HotelException hotelException -> {
                errors.add(Error.builder().message(throwable.getMessage()).build());
                status = HttpStatus.NOT_FOUND;
            }
            case null, default -> {
                errors.add(Error.builder().message(throwable.getMessage()).build());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }

        return ErrorsWrapper.builder()
                .errors(errors)
                .errorCode(status)
                .build();
    }

    public ErrorsWrapper handleErrors(Throwable throwable) {
        List<Error> errors = new ArrayList<>();
        HttpStatus status = Match(throwable).of(

                Case($(instanceOf(RoomNotAvailableException.class)), ex -> {
                    errors.add(Error.builder().message(ex.getMessage()).build());
                    return HttpStatus.CONFLICT;
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
                .errorCode(status)
                .build();
    }
}

/*
Case($(instanceOf(MethodArgumentNotValidException.class)), ex -> {
                    ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors()
                            .forEach(error -> errors.add(Error.builder()
                                    .field(error.getField())
                                    .message(error.getDefaultMessage())
                                    .build()));
                    return HttpStatus.BAD_REQUEST;
                }),
 */