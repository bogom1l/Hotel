package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomOutput;

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
    UnbookRoomOutput unbookRoom(UnbookRoomInput input);
}
