package com.tinqinacademy.hotel.core.processors.hotel;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoInput;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOperation;
import com.tinqinacademy.hotel.api.operations.hotel.getroombasicinfo.GetRoomBasicInfoOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class GetRoomBasicInfoOperationProcessor extends BaseOperationProcessor<GetRoomBasicInfoInput> implements GetRoomBasicInfoOperation {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    protected GetRoomBasicInfoOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, GetRoomBasicInfoOutput> process(GetRoomBasicInfoInput input) {
        return Try.of( () ->
                getRoomBasicInfoOutput(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private GetRoomBasicInfoOutput getRoomBasicInfoOutput(GetRoomBasicInfoInput input) {
        log.info("Started getRoomBasicInfo with input: {}", input);

        UUID roomId = UUID.fromString(input.getRoomId());
        Room room = roomRepository.findByIdWithBeds(roomId)
                .orElseThrow(() -> new HotelException("Room not found"));

        List<Booking> bookings = bookingRepository.findAllByRoomId(room.getId()).orElse(new ArrayList<>());

        List<LocalDate> datesOccupied = bookings.stream()
                .flatMap(booking -> booking.getStartDate().datesUntil(booking.getEndDate())).toList();

        GetRoomBasicInfoOutput output = conversionService
                .convert(room, GetRoomBasicInfoOutput.GetRoomBasicInfoOutputBuilder.class)
                .datesOccupied(datesOccupied).build();

        log.info("Ended getRoomBasicInfo with output: {}", output);
        return output;
    }

}
