package com.tinqinacademy.hotel.core.converters.hotel.getroombasicinfo;

import com.tinqinacademy.hotel.api.enums.BathroomType;
import com.tinqinacademy.hotel.api.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.persistence.model.Room;
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
                .bedSize(BedSize.getByCode(source.getBeds().getFirst().getBedSize().getCode()))
                .bathroomType(BathroomType.getByCode(source.getBathroomType().getCode()))
                .roomNumber(source.getRoomNumber());

        log.info("Ended Converter - Room to GetRoomBasicInfoOutput");
        return getRoomBasicInfoOutput;
    }
}
