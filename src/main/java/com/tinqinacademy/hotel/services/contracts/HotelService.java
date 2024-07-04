package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.input.GetRoomInput;
import com.tinqinacademy.hotel.model.RoomInput;
import com.tinqinacademy.hotel.model.RoomOutput;

import java.util.List;

public interface HotelService {

    String bookRoom();

    Boolean isAvailable();

    RoomOutput addRoom(RoomInput input);

    String removeRoom();

    String editRoom();

    List<String> getRooms(GetRoomInput input);
}
