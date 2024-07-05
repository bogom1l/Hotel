package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.input.RegisterReportInput;
import com.tinqinacademy.hotel.model.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.output.RegisterReportOutput;
import com.tinqinacademy.hotel.model.output.RegisterVisitorOutput;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);

    RegisterReportOutput registerReport(RegisterReportInput input);
}
