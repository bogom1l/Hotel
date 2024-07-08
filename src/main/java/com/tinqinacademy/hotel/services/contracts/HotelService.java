package com.tinqinacademy.hotel.services.contracts;

import com.tinqinacademy.hotel.model.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.model.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.model.operations.deletebooking.DeleteBookingInput;
import com.tinqinacademy.hotel.model.operations.deletebooking.DeleteBookingOutput;
import com.tinqinacademy.hotel.model.operations.getroominfo.RoomInfoInput;
import com.tinqinacademy.hotel.model.operations.getroominfo.RoomInfoOutput;
import com.tinqinacademy.hotel.model.operations.getrooms.GetRoomInput;
import com.tinqinacademy.hotel.model.operations.getrooms.GetRoomOutput;

public interface HotelService {

    BookRoomOutput bookRoom(BookRoomInput input);

    GetRoomOutput getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

    DeleteBookingOutput deleteBooking(DeleteBookingInput input);
}
