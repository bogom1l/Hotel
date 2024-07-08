package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomOutput;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);

    RegisterReportOutput registerReport(RegisterReportInput input);

    CreateRoomOutput createRoom(CreateRoomInput input);

    UpdateRoomOutput updateRoom(UpdateRoomInput input);
}
