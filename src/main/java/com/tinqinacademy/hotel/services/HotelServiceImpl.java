package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
        log.info("Start addRoom input: {}", input);

        RoomOutput output = RoomOutput.builder()
                .id(input.getId())
                .roomNumber(input.getRoomNumber())
                .bedCount(input.getBedCount())
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .floor(input.getFloor())
                .price(input.getPrice())
                .bathroomType(BathroomType.getByCode(input.getBathroomType()))
                .build();

        log.info("End addRoom output: {}", output);
        return output;
    }

    @Override
    public String getRoom(GetRoom room) {
        BedSize bedSize = BedSize.getByCode(room.getBedType());
        return "Bed Type = " + bedSize + ", Floor = " + room.getFloor();
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
