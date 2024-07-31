package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.core.services.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.*;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.UserOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GuestOutput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final BedRepository bedRepository;
    private final ConversionService conversionService;

    public SystemServiceImpl(RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository, UserRepository userRepository, BedRepository bedRepository, ConversionService conversionService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.bedRepository = bedRepository;
        this.conversionService = conversionService;
    }


    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Started updateRoom with input: {}", input);

        if (BedSize.getByCode(input.getBedSize()).equals(BedSize.UNKNOWN)) {
            throw new HotelException("No bed size found");
        }

        if (BathroomType.getByCode(input.getBathroomType()).equals(BathroomType.UNKNOWN)) {
            throw new HotelException("No bathroom type found");
        }

        if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
            throw new HotelException("Room number already exists");
        }

        if (input.getBathroomType() == null ||
                input.getBedSize() == null ||
                input.getRoomNumber() == null ||
                input.getPrice() == null) {
            throw new HotelException("Please fill all the fields.");
        }

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        room.setRoomNumber(input.getRoomNumber());
        room.setPrice(input.getPrice());

        List<Bed> bedsInCurrentRoom = room.getBeds();

        for (Bed bed : bedsInCurrentRoom) {
            bed.setBedSize(BedSize.getByCode(input.getBedSize()));
        }

        roomRepository.save(room);

        UpdateRoomOutput output = conversionService.convert(room, UpdateRoomOutput.class);

        log.info("Ended updateRoom with output: {}", output);
        return output;
    }




}