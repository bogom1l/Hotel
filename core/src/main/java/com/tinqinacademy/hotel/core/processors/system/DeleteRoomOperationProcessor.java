package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class DeleteRoomOperationProcessor extends BaseOperationProcessor<DeleteRoomInput> implements DeleteRoomOperation {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    protected DeleteRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, DeleteRoomOutput> process(DeleteRoomInput input) {
        return Try.of(() -> deleteRoom(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Started deleteRoom with input: {}", input);
        validateInput(input);

        Room room = roomRepository.findById(UUID.fromString(input.getId()))
                .orElseThrow(() -> new HotelException("No room found with id: " + input.getId()));

        bookingRepository.deleteBookingsByRoomId(room.getId());
        roomRepository.delete(room);

        //todo find all guests in the booking and delete them too.
        //todo find all beds in the room and delete them too.

        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("Ended deleteRoom with output: {}", output);
        return output;
    }


}
