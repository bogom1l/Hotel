package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorOutput;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);

    RegisterReportOutput registerReport(RegisterReportInput input);
}
