package com.tinqinacademy.hotel.exceptions;

import com.tinqinacademy.hotel.services.ErrorServiceImpl;
import com.tinqinacademy.hotel.model.error.ErrorWrapper;
import com.tinqinacademy.hotel.model.error.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorServiceImpl errorService;

    @ExceptionHandler(HotelException.class)
    public ResponseEntity<?> handleHotelException(HotelException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex){
        ErrorWrapper errors = errorService.handleErrors(ex);

        return new ResponseEntity<>(errors.getErrors(), errors.getErrorCode());
    }


}
