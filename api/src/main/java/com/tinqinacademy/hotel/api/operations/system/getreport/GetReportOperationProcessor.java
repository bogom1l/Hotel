package com.tinqinacademy.hotel.api.operations.system.getreport;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import io.vavr.control.Either;

public class GetReportOperationProcessor implements GetReportOperation{
    @Override
    public Either<ErrorsWrapper, GetReportOutput> process(GetReportInput input) {
        return null;
    }
}
