package com.tinqinacademy.hotel.core.exceptionhandler;

import com.tinqinacademy.hotel.api.error.Error;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HotelException.class)
    public ResponseEntity<?> handleHotelException(HotelException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<?> handleRoomNotAvailableException(RoomNotAvailableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // TODO: ? handle all other different exceptions

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        List<Error> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Error.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .toList();

        ErrorsWrapper errorsWrapper = ErrorsWrapper.builder()
                .errors(errors)
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(errorsWrapper.getErrors(), errorsWrapper.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        String message = "Error Message: " + ex.getMessage();

        if (ex.getCause() != null) {
            message += "\nError Cause: " + ex.getCause();
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
