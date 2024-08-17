package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOperation;
import com.tinqinacademy.hotel.api.operations.system.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.Bed;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.repository.BedRepository;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class DeleteRoomOperationProcessor extends BaseOperationProcessor<DeleteRoomInput> implements DeleteRoomOperation {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BedRepository bedRepository;
    private final GuestRepository guestRepository;

    protected DeleteRoomOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, BookingRepository bookingRepository, BedRepository bedRepository, GuestRepository guestRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.bedRepository = bedRepository;
        this.guestRepository = guestRepository;
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

        LocalDate today = LocalDate.now();
        List<Booking> allBookingsForCurrentRoom = bookingRepository.findAllByRoomId(room.getId());
        for (Booking booking : allBookingsForCurrentRoom) {
            if (booking.getEndDate().isAfter(today)) {
                throw new HotelException("Cannot delete a room with an active booking. Consider unbooking the room or waiting for the booking to end.");
            }
        }

        //find all beds in the room and delete them too (from beds table)
        List<Bed> allBedsForCurrentRoom = bedRepository.findAllByRoomId(room.getId());

        //find all guests in all past bookings of the room and delete them too (from guests table)
        List<Guest> allGuestsForCurrentRoom = new ArrayList<>();
        for(Booking booking : allBookingsForCurrentRoom) {
            List<Guest> allGuestsForCurrentBooking = guestRepository.findAllByBookingId(booking.getId());
            allGuestsForCurrentRoom.addAll(allGuestsForCurrentBooking);
        }
        
        bookingRepository.deleteBookingsByRoomId(room.getId());
        roomRepository.delete(room);

        bedRepository.deleteAll(allBedsForCurrentRoom);
        guestRepository.deleteAll(allGuestsForCurrentRoom);

        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("Ended deleteRoom with output: {}", output);
        return output;
    }
}
