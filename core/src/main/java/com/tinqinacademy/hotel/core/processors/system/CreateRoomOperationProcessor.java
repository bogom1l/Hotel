package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.enums.BathroomType;
import com.tinqinacademy.hotel.persistence.model.enums.BedSize;
import com.tinqinacademy.hotel.persistence.repository.BedRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreateRoomOperationProcessor extends BaseOperationProcessor<CreateRoomInput> implements CreateRoomOperation {
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    protected CreateRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, BedRepository bedRepository, RoomRepository roomRepository) {
        super(conversionService, errorHandler, validator);
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorsWrapper, CreateRoomOutput> process(CreateRoomInput input) {
        return Try.of(() -> createRoom(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Started createRoom with input: {}", input);
        // todo: logic: ? create the bed or find the bed

        validateInput(input);

        Bed bed = buildBed(input);
        Room room = buildRoom(input, bed);

        bedRepository.save(bed);
        roomRepository.save(room);

        CreateRoomOutput output = conversionService.convert(room, CreateRoomOutput.class);

        log.info("Ended createRoom with output: {}", output);
        return output;
    }

    private Bed buildBed(CreateRoomInput input) {
        BedSize bedSize = BedSize.getByCode(input.getBedSize());
        BathroomType bathroomType = BathroomType.getByCode(input.getBathroomType());

        if (bedSize == BedSize.UNKNOWN) {
            throw new HotelException("Invalid bed size.");
        }

        if (bathroomType == BathroomType.UNKNOWN) {
            throw new HotelException("Invalid bathroom type.");
        }

        return Bed.builder()
                .bedSize(BedSize.getByCode(input.getBedSize()))
                .capacity(BedSize.getByCode(input.getBedSize()).getCapacity())
                .build();
    }

    private Room buildRoom(CreateRoomInput input, Bed bed) {
        return conversionService
                .convert(input, Room.RoomBuilder.class)
                .beds(List.of(bed))
                .build();
    }

}
