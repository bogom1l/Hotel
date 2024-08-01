package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.api.errorhandler.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
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

@Service
@Slf4j
public class CheckAvailableRoomOperationProcessor extends BaseOperationProcessor<CheckAvailableRoomInput> implements CheckAvailableRoomOperation {
    private final RoomRepository roomRepository;

    protected CheckAvailableRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
    }

    @Override
    public Either<ErrorsWrapper, CheckAvailableRoomOutput> process(CheckAvailableRoomInput input) {
        return Try.of(() -> checkAvailableRooms(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private CheckAvailableRoomOutput checkAvailableRooms(CheckAvailableRoomInput input) {
        log.info("Started checkAvailableRoom with input: {}", input);

        validateInput(input);

        BedSize bedSize = BedSize.getByCode(input.getBedSize());
        BathroomType bathroomType = BathroomType.getByCode(input.getBathroomType());

        if (input.getStartDate().isAfter(input.getEndDate())) {
            throw new HotelException("Start date should be before end date.");
        }
        if (bedSize == BedSize.UNKNOWN) {
            throw new HotelException("Invalid bed size.");
        }

        if (bathroomType == BathroomType.UNKNOWN) {
            throw new HotelException("Invalid bathroom type.");
        }

        List<Room> availableRoomsBetweenDates = roomRepository.findAvailableRoomsBetweenDates(input.getStartDate(), input.getEndDate())
                .orElseThrow(() -> new HotelException("No available rooms found"));

        List<Room> roomsMatchingCriteria = roomRepository.findRoomsByBedSizeAndBathroomType(bedSize, bathroomType);

        List<String> availableRoomIds = availableRoomsBetweenDates.stream().filter(roomsMatchingCriteria::contains).map(room -> room.getId().toString()).toList();

        CheckAvailableRoomOutput output = conversionService.convert(availableRoomIds, CheckAvailableRoomOutput.class);

        log.info("Ended checkAvailableRoom with output: {}", output);
        return output;
    }

}
