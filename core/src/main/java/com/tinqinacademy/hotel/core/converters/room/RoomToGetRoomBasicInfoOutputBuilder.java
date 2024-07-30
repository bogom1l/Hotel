package com.tinqinacademy.hotel.core.converters.room;

import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoomToGetRoomBasicInfoOutputBuilder implements Converter<Room, GetRoomBasicInfoOutput.GetRoomBasicInfoOutputBuilder> {
    @Override
    public GetRoomBasicInfoOutput.GetRoomBasicInfoOutputBuilder convert(Room source) {
        log.info("Started Converter - Room to GetRoomBasicInfoOutput");

        GetRoomBasicInfoOutput.GetRoomBasicInfoOutputBuilder getRoomBasicInfoOutput = GetRoomBasicInfoOutput
                .builder()
                .id(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bedSize(source.getBeds().getFirst().getBedSize())
                .bathroomType(source.getBathroomType())
                .roomNumber(source.getRoomNumber());

        log.info("Ended Converter - Room to GetRoomBasicInfoOutput");
        return getRoomBasicInfoOutput;
    }
}
