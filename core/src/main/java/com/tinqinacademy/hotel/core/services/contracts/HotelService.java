package com.tinqinacademy.hotel.core.services.contracts;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;

public interface HotelService {

    //CheckAvailableRoomOutput checkAvailableRoom(CheckAvailableRoomInput input);

    //GetRoomBasicInfoOutput getRoomBasicInfo(GetRoomBasicInfoInput input);

    //BookRoomOutput bookRoom(BookRoomInput input);

    //UnbookRoomOutput unbookRoom(UnbookRoomInput input);

    void deleteAllRooms();

    void deleteAllBeds();

    //UpdatePartiallyBookingOutput updatePartiallyBooking(UpdatePartiallyBookingInput input);

    //GetBookingHistoryOutput getBookingHistory(GetBookingHistoryInput input);
}
