package com.tinqinacademy.hotel.exceptions;

import com.tinqinacademy.hotel.model.error.ErrorHandler;
import com.tinqinacademy.hotel.model.error.ErrorWrapper;
import com.tinqinacademy.hotel.model.error.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorHandler errorHandler;

    @ExceptionHandler(HotelException.class)
    public ResponseEntity<?> handleHotelException(HotelException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex){
        ErrorWrapper errors = errorHandler.handleErrors(ex);

        return new ResponseEntity<>(errors.getErrors(), errors.getErrorCode());
    }


}
