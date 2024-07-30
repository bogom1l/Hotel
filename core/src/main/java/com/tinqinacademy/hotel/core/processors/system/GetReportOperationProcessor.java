package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOperation;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetReportOperationProcessor implements GetReportOperation {
    @Override
    public Either<ErrorsWrapper, GetReportOutput> process(GetReportInput input) {
        return null;
    }
}
