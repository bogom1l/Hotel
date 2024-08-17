package com.tinqinacademy.hotel.core.converters.system.createroom;

import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoomToCreateRoomOutput implements Converter<Room, CreateRoomOutput> {
    @Override
    public CreateRoomOutput convert(Room source) {
        log.info("Started Converter - Room to CreateRoomOutput");

        CreateRoomOutput createRoomOutput = CreateRoomOutput.builder()
                .id(source.getId())
                .build();

        log.info("Ended Converter - Room to CreateRoomOutput");
        return createRoomOutput;
    }
}
