package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.deletebooking.DeleteBookingInput;
import com.tinqinacademy.hotel.api.operations.deletebooking.DeleteBookingOutput;
import com.tinqinacademy.hotel.api.operations.getroominfo.RoomInfoInput;
import com.tinqinacademy.hotel.api.operations.getroominfo.RoomInfoOutput;
import com.tinqinacademy.hotel.api.operations.getrooms.GetRoomInput;
import com.tinqinacademy.hotel.api.operations.getrooms.GetRoomOutput;
import com.tinqinacademy.hotel.persistence.models.Bed;
import com.tinqinacademy.hotel.persistence.models.User;

import java.util.List;

public interface HotelService {

    BookRoomOutput bookRoom(BookRoomInput input);

    GetRoomOutput getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

    DeleteBookingOutput deleteBooking(DeleteBookingInput input);

    List<User> findAllUsers();

    List<Bed> findAllBeds();

    void addBed(Bed bed);
}
