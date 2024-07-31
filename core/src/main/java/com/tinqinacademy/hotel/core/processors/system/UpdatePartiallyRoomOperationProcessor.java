package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.updatepartiallyroom.UpdatePartiallyRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UpdatePartiallyRoomOperationProcessor extends BaseOperationProcessor<UpdatePartiallyRoomInput> implements UpdatePartiallyRoomOperation {
    private final RoomRepository roomRepository;

    protected UpdatePartiallyRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorsWrapper, UpdatePartiallyRoomOutput> process(UpdatePartiallyRoomInput input) {
        return Try.of(() -> updatePartiallyRoom(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private UpdatePartiallyRoomOutput updatePartiallyRoom(UpdatePartiallyRoomInput input) {
        log.info("Started updatePartiallyRoom with input: {}", input);

        validateInput(input);

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        setFields(input, room);

        roomRepository.save(room);

        UpdatePartiallyRoomOutput output = conversionService.convert(room, UpdatePartiallyRoomOutput.class);

        log.info("Ended updatePartiallyRoom with output: {}", output);
        return output;
    }

    private void setFields(UpdatePartiallyRoomInput input, Room room) {

        if (input.getBathroomType() != null) {
            if (BathroomType.getByCode(input.getBathroomType()).equals(BathroomType.UNKNOWN)) {
                throw new HotelException("No bathroom type found");
            }

            room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        }

        if (input.getRoomNumber() != null) {
            if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
                throw new HotelException("Room number already exists");
            }
            room.setRoomNumber(input.getRoomNumber());
        }

        if (input.getPrice() != null) {
            room.setPrice(input.getPrice());
        }

        if (input.getBedSize() != null) {
            if (BedSize.getByCode(input.getBedSize()).equals(BedSize.UNKNOWN)) {
                throw new HotelException("No bed size found");
            }

            List<Bed> bedsInCurrentRoom = room.getBeds();

            for (Bed bed : bedsInCurrentRoom) {
                bed.setBedSize(BedSize.getByCode(input.getBedSize()));
            }
        }
    }
}
