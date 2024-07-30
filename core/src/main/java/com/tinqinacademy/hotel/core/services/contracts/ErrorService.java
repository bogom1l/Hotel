package com.tinqinacademy.hotel.core.services.contracts;

import com.tinqinacademy.hotel.api.error.ErrorWrapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface ErrorService {
    ErrorWrapper handleErrors(MethodArgumentNotValidException ex);
}
