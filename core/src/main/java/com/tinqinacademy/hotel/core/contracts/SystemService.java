package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;

public interface SystemService {
//    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
//
//    RegisterReportOutput registerReport(RegisterReportInput input);
//
//    CreateRoomOutput createRoom(CreateRoomInput input);
//
//    UpdateRoomOutput updateRoom(UpdateRoomInput input);
//
//    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
//
//    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

    RegisterGuestOutput registerGuest(RegisterGuestInput input);

    GetReportOutput getReport(GetReportInput input);

}
