package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.api.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;

public interface SystemService {

    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);

    RegisterReportOutput registerReport(RegisterReportInput input);

    CreateRoomOutput createRoom(CreateRoomInput input);

    UpdateRoomOutput updateRoom(UpdateRoomInput input);

    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);

    DeleteRoomOutput deleteRoom(DeleteRoomInput input);
}
