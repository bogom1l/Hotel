package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOperation;
import com.tinqinacademy.hotel.api.operations.hotel.unbookroom.UnbookRoomOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
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

    protected UnbookRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.bookingRepository = bookingRepository;
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

        UUID bookingId = UUID.fromString(input.getBookingId());
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new HotelException("Booking not found"));

        bookingRepository.delete(booking);

        UnbookRoomOutput output = UnbookRoomOutput.builder().build();

        log.info("Ended unbookRoom with output: {}", output);
        return output;
    }

}
