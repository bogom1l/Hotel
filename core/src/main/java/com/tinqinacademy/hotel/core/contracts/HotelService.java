package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.getroombasicinfo.GetRoomBasicInfoOutput;

public interface HotelService {

    /*
    BookRoomOutput bookRoom(BookRoomInput input);

    GetRoomOutput getRooms(GetRoomInput input);

    RoomInfoOutput getRoomInfo(RoomInfoInput input);

    DeleteBookingOutput deleteBooking(DeleteBookingInput input);
*/
    CheckAvailableRoomOutput checkAvailableRoom(CheckAvailableRoomInput input);

    GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input);

    BookRoomOutput bookRoom(BookRoomInput input);
}
