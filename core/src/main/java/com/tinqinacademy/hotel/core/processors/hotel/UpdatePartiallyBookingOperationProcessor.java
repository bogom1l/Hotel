package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingInput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOperation;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyGuestInput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
public class UpdatePartiallyBookingOperationProcessor extends BaseOperationProcessor<UpdatePartiallyBookingInput> implements UpdatePartiallyBookingOperation {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    protected UpdatePartiallyBookingOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository, GuestRepository guestRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorsWrapper, UpdatePartiallyBookingOutput> process(UpdatePartiallyBookingInput input) {
        return Try.of(() -> updatePartiallyBooking(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private UpdatePartiallyBookingOutput updatePartiallyBooking(UpdatePartiallyBookingInput input) {
        log.info("Started updatePartiallyBooking with input: {}", input);

        validateInput(input);

        Booking booking = bookingRepository.findById(UUID.fromString(input.getBookingId()))
                .orElseThrow(() -> new HotelException("Booking not found"));

        setFields(input, booking);

        bookingRepository.save(booking);

        UpdatePartiallyBookingOutput output = conversionService.convert(booking, UpdatePartiallyBookingOutput.class);

        log.info("Ended updatePartiallyBooking with output: {}", output);
        return output;
    }

    private void setFields(UpdatePartiallyBookingInput input, Booking booking) {
        if (input.getStartDate() != null) {
            booking.setStartDate(LocalDate.parse(input.getStartDate()));
        }

        if (input.getEndDate() != null) {
            booking.setEndDate(LocalDate.parse(input.getEndDate()));
        }

        if (input.getTotalPrice() != null) {
            booking.setTotalPrice(input.getTotalPrice());
        }

        if (input.getRoomNumber() != null) {
            if (roomRepository.existsByRoomNumber(input.getRoomNumber())) {
                throw new HotelException("Room number already exists. Please choose different room number.");
            }

            booking.getRoom().setRoomNumber(input.getRoomNumber()); // logic: shouldn't be able to create a new room
        }

        if (input.getGuests() != null && !input.getGuests().isEmpty()) { // if guests field is not left empty
            for (UpdatePartiallyGuestInput guest : input.getGuests()) { // add each of the filled guests
                Guest newGuest = conversionService.convert(guest, Guest.class);

                guestRepository.save(newGuest);

                booking.getGuests().add(newGuest);
            }
        }

    }

}
