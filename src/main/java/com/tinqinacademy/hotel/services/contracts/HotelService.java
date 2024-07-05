package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.input.GetRoomInput;
import com.tinqinacademy.hotel.model.input.RoomInfoInput;
import com.tinqinacademy.hotel.model.input.RoomInput;
import com.tinqinacademy.hotel.model.output.RoomInfoOutput;
import com.tinqinacademy.hotel.model.output.RoomOutput;

import java.util.List;

public interface HotelService {

    String bookRoom();

    Boolean isAvailable();

    RoomOutput addRoom(RoomInput input);

    String removeRoom();

    String editRoom();

    List<String> getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

}
