//package com.tinqinacademy.hotel.core.converters.room;
//
//
//import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
//import com.tinqinacademy.hotel.persistence.model.Room;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class RoomToUpdatePartiallyRoomOutput implements Converter<Room, UpdatePartiallyRoomOutput> {
//    @Override
//    public UpdatePartiallyRoomOutput convert(Room source) {
//        log.info("Started Converter - Room to UpdatePartiallyRoomOutput");
//
//        UpdatePartiallyRoomOutput updatePartiallyRoomOutput = UpdatePartiallyRoomOutput.builder()
//                .id(source.getId())
//                .build();
//
//        log.info("Ended Converter - Room to UpdatePartiallyRoomOutput");
//        return updatePartiallyRoomOutput;
//    }
//}