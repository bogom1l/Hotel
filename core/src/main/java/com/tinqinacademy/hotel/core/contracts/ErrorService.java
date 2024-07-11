package com.tinqinacademy.hotel.core.contracts;

//import com.tinqinacademy.hotel.model.error.ErrorWrapper;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.tinqinacademy.hotel.api.error.ErrorWrapper;

public interface ErrorService {
    ErrorWrapper handleErrors(MethodArgumentNotValidException ex);
    // TODO ? : ErrorWrapper handleErrors(Exception ex);
}
