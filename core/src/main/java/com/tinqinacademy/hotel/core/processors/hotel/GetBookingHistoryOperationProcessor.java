package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryBookingOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryInput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryOutput;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GetBookingHistoryOperationProcessor extends BaseOperationProcessor<GetBookingHistoryInput> implements GetBookingHistoryOperation {
    private final BookingRepository bookingRepository;

    protected GetBookingHistoryOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, GetBookingHistoryOutput> process(GetBookingHistoryInput input) {
        return Try.of(() -> getBookingHistoryOutput(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private GetBookingHistoryOutput getBookingHistoryOutput(GetBookingHistoryInput input) {
        log.info("Started getBookingHistory with input: {}", input);
        validateInput(input);

        List<Booking> bookings = bookingRepository.findAllByUserId(UUID.fromString(input.getUserId()));

        List<GetBookingHistoryBookingOutput> bookingsOutput = bookings.stream()
                .map(booking -> conversionService.convert(booking, GetBookingHistoryBookingOutput.class))
                .toList();

        GetBookingHistoryOutput output = GetBookingHistoryOutput.builder()
                .bookings(bookingsOutput)
                .build();

        log.info("Ended getBookingHistory with output: {}", output);
        return output;
    }
}
