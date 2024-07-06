package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.registervisitor.RegisterVisitorOutput;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);

    RegisterReportOutput registerReport(RegisterReportInput input);
}
