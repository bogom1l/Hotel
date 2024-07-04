package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.GetRoom;
import com.tinqinacademy.hotel.model.RoomInput;
import com.tinqinacademy.hotel.model.RoomOutput;

public interface HotelService {

    String bookRoom();

    Boolean isAvailable();

    RoomOutput addRoom(RoomInput input);

    String removeRoom();

    String editRoom();

    String getRoom(GetRoom room);
}
