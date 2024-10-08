package com.tinqinacademy.hotel.core.converters.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryRoomOutput;
import com.tinqinacademy.hotel.persistence.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoomToGetBookingHistoryRoomOutput implements Converter<Room, GetBookingHistoryRoomOutput> {
    @Override
    public GetBookingHistoryRoomOutput convert(Room source) {
        log.info("Started Converter - Room to GetBookingRoomOutput");

        GetBookingHistoryRoomOutput target = GetBookingHistoryRoomOutput.builder()
                .roomNumber(source.getRoomNumber())
                .roomPrice(source.getPrice())
                .roomFloor(source.getFloor())
                .build();

        log.info("Ended Converter - Room to GetBookingRoomOutput");
        return target;
    }
}
