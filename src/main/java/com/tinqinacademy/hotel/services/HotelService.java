package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.GetRoomInput;
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
