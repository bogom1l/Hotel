package com.tinqinacademy.hotel.core.converters.room;


import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RoomToUpdateRoomOutput implements Converter<Room, UpdateRoomOutput> {
    @Override
    public UpdateRoomOutput convert(Room source) {
        log.info("Started Converter - Room to UpdateRoomOutput");

        UpdateRoomOutput updateRoomOutput = UpdateRoomOutput.builder()
                .id(source.getId())
                .build();

        log.info("Ended Converter - Room to UpdateRoomOutput");
        return updateRoomOutput;
    }
}