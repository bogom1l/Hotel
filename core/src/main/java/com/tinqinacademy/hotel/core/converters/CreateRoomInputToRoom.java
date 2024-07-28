package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.operations.system.createroom.CreateRoomInput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateRoomInputToRoom implements Converter<CreateRoomInput, Room> {

    @Override
    public Room convert(CreateRoomInput source) {
        return Room.builder()
                .bathroomType(BathroomType.getByCode(source.getBathroomType()))
                .floor(source.getFloor())
                .roomNumber(source.getRoomNumber())
                .price(source.getPrice())
                .build();
    }
}
