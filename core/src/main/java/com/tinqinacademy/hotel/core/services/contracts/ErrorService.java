package com.tinqinacademy.hotel.core.services.contracts;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorService {
    ErrorsWrapper handleMethodNotValidException(MethodArgumentNotValidException ex);
}
