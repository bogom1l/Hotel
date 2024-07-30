package com.tinqinacademy.hotel.api.operations.hotel.bookroom;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.exceptions.RoomNotAvailableException;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class BookRoomOperationProcessor implements BookRoomOperation {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final ErrorHandler errorHandler;

    public BookRoomOperationProcessor(RoomRepository roomRepository, UserRepository userRepository, BookingRepository bookingRepository, ErrorHandler errorHandler) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.errorHandler = errorHandler;
    }

    @Override
    public Either<ErrorsWrapper, BookRoomOutput> process(BookRoomInput input) {
        //Try.of() block -> .toEither.mapLeft(errors)
        //na baza exceptionite, da vurna ErrorsWrapper
        return Try.of(() -> {

                    log.info("Started bookRoom with input: {}", input);

                    Room room = findRoom(input);

                    checkRoomAvailability(room, input.getStartDate(), input.getEndDate());

                    User user = findUser(input); // todo: logic: find user or create user?

                    Booking booking = Booking
                            .builder()
                            .room(room)
                            .user(user)
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .totalPrice(room.getPrice())
                            .guests(Set.of()) // Empty set, later will have endpoint for adding guests for certain booking
                            .build();

                    bookingRepository.save(booking);

                    BookRoomOutput output = BookRoomOutput.builder().build();

                    log.info("Ended bookRoom with output: {}", output);

                    return output; //todo
                })
                .toEither()
                .mapLeft(throwable -> {
                    log.error("Error occurred while booking room: {}", throwable.getMessage());
                    return errorHandler.handleErrors(throwable);
                });

    }

    private Room findRoom(BookRoomInput input) {
        return roomRepository
                .findById(UUID.fromString(input.getRoomId()))
                .orElseThrow(() -> new HotelException("Room not found"));
    }

    private void checkRoomAvailability(Room room, LocalDate startDate, LocalDate endDate) {
        if (!roomRepository.isRoomAvailableByRoomIdAndBetweenDates(room.getId(), startDate, endDate)) {
            throw new RoomNotAvailableException(String
                    .format("Room with ID %s is not available for the selected dates: %s - %s",
                            room.getId(), startDate, endDate));
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
