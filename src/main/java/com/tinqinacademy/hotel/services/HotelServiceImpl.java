package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.RoomInput;
import com.tinqinacademy.hotel.model.RoomOutput;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService{

    @Override
    public String bookRoom() {
        return "You booked a room";
    }

    @Override
    public Boolean isAvailable() {
        return true;
    }

    @Override
    public RoomOutput addRoom(RoomInput input) {
        RoomOutput output = RoomOutput.builder()
                .id(input.getId())
                .roomNumber(input.getRoomNumber())
                .bedCount(input.getBedCount())
                .bedSize(input.getBedSize())
                .floor(input.getFloor())
                .price(input.getPrice())
                .bathroomType(input.getBathroomType())
        .build();

        return output;
    }

    @Override
    public String removeRoom() {
        return "You removed a room.";
    }

    @Override
    public String editRoom() {
        return "You edited a room";
    }
}
