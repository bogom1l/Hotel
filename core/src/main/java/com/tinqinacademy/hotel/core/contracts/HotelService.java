package com.tinqinacademy.hotel.core.contracts;

import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;

public interface HotelService {

    CheckAvailableRoomOutput checkAvailableRoom(CheckAvailableRoomInput input);

    GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input);

    BookRoomOutput bookRoom(BookRoomInput input);

    UnbookRoomOutput unbookRoom(UnbookRoomInput input);

    void deleteAllRooms();

    void deleteAllBeds();

    UpdatePartiallyBookingOutput updatePartiallyBooking(UpdatePartiallyBookingInput input);
}
