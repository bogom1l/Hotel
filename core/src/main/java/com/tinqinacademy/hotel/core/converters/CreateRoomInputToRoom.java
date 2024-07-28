package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CreateRoomInputToRoom implements Converter<CreateRoomInput, Room> {

    @Override
    public Room convert(CreateRoomInput source) {
        log.info("Started Converter - CreateRoomInput to Room");

        Room room = Room.builder()
                .bathroomType(BathroomType.getByCode(source.getBathroomType()))
                .floor(source.getFloor())
                .roomNumber(source.getRoomNumber())
                .price(source.getPrice())
                .build();

        log.info("Ended Converter - CreateRoomInput to Room");
        return room;
    }
}
