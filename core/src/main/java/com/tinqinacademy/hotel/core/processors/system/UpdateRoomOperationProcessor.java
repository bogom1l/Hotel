package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
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
public class UpdateRoomOperationProcessor extends BaseOperationProcessor<UpdateRoomInput> implements UpdateRoomOperation {
    private final RoomRepository roomRepository;

    protected UpdateRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorsWrapper, UpdateRoomOutput> process(UpdateRoomInput input) {
        return Try.of(() -> updateRoom(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Started updateRoom with input: {}", input);

        validateInput(input);

        validateFields(input);

        Room room = roomRepository.findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getRoomId()));

        setFields(input, room);

        UpdateRoomOutput output = conversionService.convert(room, UpdateRoomOutput.class);

        log.info("Ended updateRoom with output: {}", output);
        return output;
    }

    private void setFields(UpdateRoomInput input, Room room) {
        room.setBathroomType(BathroomType.getByCode(input.getBathroomType()));
        room.setRoomNumber(input.getRoomNumber());
        room.setPrice(input.getPrice());

        List<Bed> bedsInCurrentRoom = room.getBeds();

        for (Bed bed : bedsInCurrentRoom) {
            bed.setBedSize(BedSize.getByCode(input.getBedSize()));
        }

        roomRepository.save(room);
    }

    private void validateFields(UpdateRoomInput input) {
        if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
            throw new HotelException("Room number already exists");
        }

        if (input.getBathroomType() == null ||
                input.getBedSize() == null ||
                input.getRoomNumber() == null ||
                input.getPrice() == null) {
            throw new HotelException("Please fill all the fields.");
        }
    }
}
