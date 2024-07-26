package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.updateroom.UpdateRoomOutput;

public interface SystemService {

    RegisterGuestOutput registerGuest(RegisterGuestInput input);

    GetReportOutput getReport(GetReportInput input);

    CreateRoomOutput createRoom(CreateRoomInput input);

    UpdateRoomOutput updateRoom(UpdateRoomInput input);

    UpdatePartiallyRoomOutput updatePartiallyRoom(UpdatePartiallyRoomInput input);

    DeleteRoomOutput deleteRoom(DeleteRoomInput input);
}
