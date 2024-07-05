package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.enums.BathroomType;
import com.tinqinacademy.hotel.model.enums.BedSize;
import com.tinqinacademy.hotel.model.input.*;
import com.tinqinacademy.hotel.model.output.BookRoomOutput;
import com.tinqinacademy.hotel.model.output.DeleteBookingOutput;
import com.tinqinacademy.hotel.model.output.RoomInfoOutput;
import com.tinqinacademy.hotel.model.output.RoomOutput;
import com.tinqinacademy.hotel.services.contracts.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {



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
    public String removeRoom() {
        return "You removed a room.";
    }

    @Override
    public String editRoom() {
        return "You edited a room";
    }

    @Override
    public List<String> getRooms(GetRoomInput input) {
        log.info("GetRooms called with input: {}", input);

        List<String> rooms = List.of("1", "2", "3");

        log.info("getRooms returned: {}", rooms);
        return rooms;
    }

    @Override
    public RoomInfoOutput getRoomInfo(RoomInfoInput input) {
        log.info("getRoomInfo called with input: {}", input);

        Random random = new Random();
        List<LocalDate> sampleDates = GenerateSampleDates();

        RoomInfoOutput output = RoomInfoOutput.builder() // with sample random data
                .id(input.getRoomId())
                .price(BigDecimal.valueOf(random.nextDouble(50_000) + 1))
                .floor(random.nextInt(10) + 1)
                .bedSize(BedSize.DOUBLE)
                .bathroomType(BathroomType.PRIVATE)
                .bedCount(random.nextInt(5) + 1)
                .datesOccupied(sampleDates)
                .build();

        log.info("getRoomInfo returned: {}", output);
        return output;
    }


    private List<LocalDate> GenerateSampleDates(){
        List<LocalDate> sampleDates = new ArrayList<>();
        sampleDates.add(LocalDate.now());
        sampleDates.add(LocalDate.now().plusDays(1));
        sampleDates.add(LocalDate.now().plusDays(2));
        sampleDates.add(LocalDate.now().plusDays(3));

        return sampleDates;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("bookRoom called with input: {}", input);

        BookRoomOutput output = BookRoomOutput.builder().build();

        log.info("bookRoom returned: {}", output);
        return output;
    }

    @Override
    public DeleteBookingOutput deleteBooking(DeleteBookingInput input) {
        log.info("deleteBooking called with input: {}", input);

        DeleteBookingOutput output = DeleteBookingOutput.builder().build();

        log.info("deleteBooking returned: {}", output);
        return output;
    }

}
