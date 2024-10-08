package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class BookRoomOperationProcessor extends BaseOperationProcessor<BookRoomInput> implements BookRoomOperation {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    protected BookRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, BookRoomOutput> process(BookRoomInput input) {
        return Try.of(() -> bookRoom(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Started bookRoom with input: {}", input);
        validateInput(input);

        Room room = roomRepository
                .findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("Room not found"));

        checkRoomAvailability(input, room);

        Booking booking = conversionService.convert(input, Booking.BookingBuilder.class)
                .room(room)
                .totalPrice(room.getPrice())
                .guests(Set.of())
                .build(); // Empty set, because later user will be able to add guests to booking (RegisterGuestOperation)

        bookingRepository.save(booking);

        BookRoomOutput output = BookRoomOutput.builder().build();
        log.info("Ended bookRoom with output: {}", output);
        return output;
    }

    private void checkRoomAvailability(BookRoomInput input, Room room) {
        if (!roomRepository.isRoomAvailableByRoomIdAndBetweenDates(room.getId(), input.getStartDate(), input.getEndDate())) {
            throw new RoomNotAvailableException(
                    String.format("Room with ID %s is not available for the selected dates: %s - %s",
                            room.getId(), input.getStartDate(), input.getEndDate()));
        }
    }
}
