package com.tinqinacademy.hotel.core.exceptionhandler;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.core.services.contracts.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorService errorService;

    @ExceptionHandler(HotelException.class)
    public ResponseEntity<?> handleHotelException(HotelException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // TODO: handle all other different exceptions

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodNotValidException(MethodArgumentNotValidException ex) {
        ErrorsWrapper errors = errorService.handleMethodNotValidException(ex);

        return new ResponseEntity<>(errors.getErrors(), errors.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        String message = ex.getMessage();
        if (ex.getCause() != null) {
            message += "\nCause: " + ex.getCause();
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
