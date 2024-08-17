package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UnbookRoomOperationProcessor extends BaseOperationProcessor<UnbookRoomInput> implements UnbookRoomOperation {
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;

    protected UnbookRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, BookingRepository bookingRepository, GuestRepository guestRepository) {
        super(conversionService, errorHandler, validator);
        this.bookingRepository = bookingRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public Either<ErrorsWrapper, UnbookRoomOutput> process(UnbookRoomInput input) {
        return Try.of(() -> unbookRoomOutput(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private UnbookRoomOutput unbookRoomOutput(UnbookRoomInput input) {
        log.info("Started unbookRoom with input: {}", input);
        validateInput(input);

        Booking booking = bookingRepository.findById(UUID.fromString(input.getBookingId()))
                .orElseThrow(() -> new HotelException("Booking not found"));

        if(!input.getUserId().equals(booking.getUserId().toString())) {
            throw new HotelException("This user is not the creator of the booking, thus he isn't authorized to delete it");
        }

        for (Guest guest : booking.getGuests()) {
            bookingRepository.deleteFromBookingsGuestsByBookingId(UUID.fromString(input.getBookingId()));
            guestRepository.deleteById(guest.getId());
        }
        bookingRepository.delete(booking);

        UnbookRoomOutput output = UnbookRoomOutput.builder().build();
        log.info("Ended unbookRoom with output: {}", output);
        return output;
    }

}
