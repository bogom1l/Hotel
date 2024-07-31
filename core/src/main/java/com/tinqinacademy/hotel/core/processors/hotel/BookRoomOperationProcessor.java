package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
//    private final ErrorHandler errorHandler;

    protected BookRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator); //
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
//        this.errorHandler = errorHandler;
    }

    @Override
    public Either<ErrorsWrapper, BookRoomOutput> process(BookRoomInput input) {
        return Try.of(() ->
                        bookRoom(input)
                )
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private BookRoomOutput bookRoom(BookRoomInput input) {
        log.info("Started bookRoom with input: {}", input);

        validateInput(input);

        Room room = findRoom(input);

        checkRoomAvailability(input, room);

        User user = findUser(input); // todo: logic: ? find user or create user

        Booking booking = buildBooking(input, room, user);

        bookingRepository.save(booking);

        BookRoomOutput output = BookRoomOutput.builder().build();

        log.info("Ended bookRoom with output: {}", output);
        return output;
    }

    private Booking buildBooking(BookRoomInput input, Room room, User user) {
        return Booking
                .builder()
                .room(room)
                .user(user)
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .totalPrice(room.getPrice())
                .guests(Set.of()) // Empty set, later will have endpoint for adding guests for certain booking
                .build();
    }

    private Room findRoom(BookRoomInput input) {
        return roomRepository
                .findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("Room not found"));
    }

    private void checkRoomAvailability(BookRoomInput input, Room room) {
        if (!roomRepository.isRoomAvailableByRoomIdAndBetweenDates(room.getId(), input.getStartDate(), input.getEndDate())) {
            throw new RoomNotAvailableException(
                    String.format("Room with ID %s is not available for the selected dates: %s - %s",
                            room.getId(), input.getStartDate(), input.getEndDate()));
        }
    }

    private User findUser(BookRoomInput input) {
        return userRepository
                .findByPhoneNumberAndFirstNameAndLastName(
                        input.getPhoneNumber(),
                        input.getFirstName(),
                        input.getLastName())
                .orElseThrow(() -> new HotelException(
                        String.format("No user found with first name: %s, last name: %s, phone number: %s",
                                input.getFirstName(),
                                input.getLastName(),
                                input.getPhoneNumber())));
    }

}