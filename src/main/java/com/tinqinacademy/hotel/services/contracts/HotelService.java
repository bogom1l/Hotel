package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.model.deletebooking.DeleteBookingInput;
import com.tinqinacademy.hotel.model.getroominfo.RoomInfoInput;
import com.tinqinacademy.hotel.model.getrooms.GetRoomInput;
import com.tinqinacademy.hotel.model.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.model.deletebooking.DeleteBookingOutput;
import com.tinqinacademy.hotel.model.getroominfo.RoomInfoOutput;

import java.util.List;

public interface HotelService {

    BookRoomOutput bookRoom(BookRoomInput input);

    List<String> getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

    DeleteBookingOutput deleteBooking(DeleteBookingInput input);
}
