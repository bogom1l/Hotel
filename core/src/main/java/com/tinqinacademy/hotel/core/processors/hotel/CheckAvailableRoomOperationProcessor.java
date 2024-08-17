package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.checkavailableroom.CheckAvailableRoomOutput;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
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

import java.time.LocalDate;
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

        LocalDate startDate = input.getStartDate();
        LocalDate endDate = input.getEndDate();
        BedSize bedSize = input.getBedSize() != null ? BedSize.getByCode(input.getBedSize()) : null;
        BathroomType bathroomType = input.getBathroomType() != null ? BathroomType.getByCode(input.getBathroomType()) : null;

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new HotelException("Start date should be before end date.");
        }

        List<Room> availableRooms = roomRepository.findAvailableRoomsBetweenDates(startDate, endDate)
                .orElseThrow(() -> new HotelException("No available rooms found"));

        List<Room> filteredRooms = availableRooms.stream()
                .filter(room -> bedSize == null || room.getBeds().stream().anyMatch(bed -> bed.getBedSize().equals(bedSize)))
                .filter(room -> bathroomType == null || room.getBathroomType().equals(bathroomType))
                .toList();

        CheckAvailableRoomOutput output = conversionService.convert(filteredRooms, CheckAvailableRoomOutput.class);

        log.info("Ended checkAvailableRoom with output: {}", output);
        return output;
    }

}
