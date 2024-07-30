package com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class GetBookingHistoryOperationProcessor implements GetBookingHistoryOperation{
    @Override
    public Either<ErrorsWrapper, GetBookingHistoryOutput> process(GetBookingHistoryInput input) {
        return null;
    }
}
