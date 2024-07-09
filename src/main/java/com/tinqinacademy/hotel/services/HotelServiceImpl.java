package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.enums.BathroomType;
import com.tinqinacademy.hotel.model.enums.BedSize;
import com.tinqinacademy.hotel.model.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.model.operations.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.model.operations.deletebooking.DeleteBookingInput;
import com.tinqinacademy.hotel.model.operations.deletebooking.DeleteBookingOutput;
import com.tinqinacademy.hotel.model.operations.getroominfo.RoomInfoInput;
import com.tinqinacademy.hotel.model.operations.getroominfo.RoomInfoOutput;
import com.tinqinacademy.hotel.model.operations.getrooms.GetRoomInput;
import com.tinqinacademy.hotel.model.operations.getrooms.GetRoomOutput;
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
    public GetRoomOutput getRooms(GetRoomInput input) {
        log.info("Start getRooms with input: {}", input);

        GetRoomOutput output = GetRoomOutput.builder()
                .ids(List.of("1", "2", "3", "63"))
                .build();

        log.info("End getRooms with output: {}", output);
        return output;
    }

    @Override
    public RoomInfoOutput getRoomInfo(RoomInfoInput input) {
        log.info("Start getRoomInfo with input: {}", input);

        Random random = new Random();
        List<LocalDate> sampleDates = generateSampleDates();

        RoomInfoOutput output = RoomInfoOutput.builder() // sample random data
                .id(input.getRoomId())
                .price(BigDecimal.valueOf(random.nextDouble(50_000) + 1))
                .floor(random.nextInt(10) + 1)
                .bedSize(BedSize.DOUBLE)
                .bathroomType(BathroomType.PRIVATE)
                .bedCount(random.nextInt(5) + 1)
                .datesOccupied(sampleDates)
                .build();

        log.info("End getRoomInfo with output: {}", output);
        return output;
    }

    private List<LocalDate> generateSampleDates() {
        List<LocalDate> sampleDates = new ArrayList<>();
        sampleDates.add(LocalDate.now());
        sampleDates.add(LocalDate.now().plusDays(1));
        sampleDates.add(LocalDate.now().plusDays(2));
        sampleDates.add(LocalDate.now().plusDays(3));

        return sampleDates;
    }

    @Override
    public BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Start bookRoom with input: {}", input);

        BookRoomOutput output = BookRoomOutput.builder().build();

        log.info("End bookRoom with output: {}", output);
        return output;
    }

    @Override
    public DeleteBookingOutput deleteBooking(DeleteBookingInput input) {
        log.info("Start deleteBooking with input: {}", input);

        DeleteBookingOutput output = DeleteBookingOutput.builder().build();

        log.info("End deleteBooking with output: {}", output);
        return output;
    }

}