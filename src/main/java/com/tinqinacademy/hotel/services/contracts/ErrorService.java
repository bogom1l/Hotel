package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.error.ErrorWrapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorService {
    ErrorWrapper handleErrors(MethodArgumentNotValidException ex);
}
