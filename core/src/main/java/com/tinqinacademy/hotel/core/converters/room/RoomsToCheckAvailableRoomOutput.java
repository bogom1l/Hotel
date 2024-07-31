package com.tinqinacademy.hotel.core.converters.room;

import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RoomsToCheckAvailableRoomOutput implements Converter<List<Room>, CheckAvailableRoomOutput> {

    @Override
    public CheckAvailableRoomOutput convert(List<Room> source) {
        log.info("Started Converter - Room to CheckAvailableRoomOutput");

        List<String> ids = source.stream().map(room -> room.getId().toString()).toList();

        CheckAvailableRoomOutput checkAvailableRoomOutput =
                CheckAvailableRoomOutput
                        .builder()
                        .ids(ids)
                        .build();

        log.info("Ended Converter - Room to CheckAvailableRoomOutput");
        return checkAvailableRoomOutput;
    }
}