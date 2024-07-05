package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.input.*;
import com.tinqinacademy.hotel.model.output.BookRoomOutput;
import com.tinqinacademy.hotel.model.output.DeleteBookingOutput;
import com.tinqinacademy.hotel.model.output.RoomInfoOutput;
import com.tinqinacademy.hotel.model.output.RoomOutput;

import java.util.List;

public interface HotelService {

    BookRoomOutput bookRoom(BookRoomInput input);

    Boolean isAvailable();

    RoomOutput addRoom(RoomInput input);

    String removeRoom();

    String editRoom();

    List<String> getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

    DeleteBookingOutput deleteBooking(DeleteBookingInput input);
}
