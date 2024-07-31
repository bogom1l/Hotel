package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOperation;
import com.tinqinacademy.hotel.api.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
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
public class RegisterGuestOperationProcessor extends BaseOperationProcessor<RegisterGuestInput> implements RegisterGuestOperation {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;


    protected RegisterGuestOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, RegisterGuestOutput> process(RegisterGuestInput input) {
        return Try.of(() -> registerGuest(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Started registerGuest with input: {}", input);

        validateInput(input);

        // todo: logic: should room be in the List, or should it be a separate field?

        for (GuestInput guestInput : input.getGuests()) {
            Room room = roomRepository.findById(UUID.fromString(guestInput.getRoomId()))
                    .orElseThrow(() -> new HotelException("No room found"));

            Guest guest = conversionService.convert(guestInput, Guest.class);

            Booking booking = bookingRepository.findByRoomIdAndStartDateAndEndDate(
                            room.getId(), guestInput.getStartDate(), guestInput.getEndDate())
                    .orElseThrow(() -> new HotelException("No booking found"));

            booking.getGuests().add(guest);

            guestRepository.save(guest);
            bookingRepository.save(booking);
        }

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("Ended registerGuest with output: {}", output);
        return output;
    }
}
