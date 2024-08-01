package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.core.errorhandler.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.exceptions.HotelException;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportInput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOperation;
import com.tinqinacademy.hotel.api.operations.system.getreport.GetReportOutput;
import com.tinqinacademy.hotel.api.operations.system.getreport.GuestOutput;
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

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class GetReportOperationProcessor extends BaseOperationProcessor<GetReportInput> implements GetReportOperation {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;


    protected GetReportOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository) {
        super(conversionService, errorHandler, validator);
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Either<ErrorsWrapper, GetReportOutput> process(GetReportInput input) {
        return Try.of(() -> getReport(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }


    private GetReportOutput getReport(GetReportInput input) {
        log.info("Started getRoomReport with input: {}", input);

        validateInput(input);

        if (!isAnyFieldProvided(input)) {
            throw new HotelException("No search criteria provided."); // optional fields - fill only 1) or 2) or 3)
        }

        Map<UUID, GuestOutput> guestMap = new HashMap<>();

        if (isDateRangeProvided(input)) {
            guestMap.putAll(filterByDateRange(input)); //1. Search by startDate and endDate
        }

        if (isGuestDetailsProvided(input)) {
            guestMap.putAll(filterByGuestDetails(input)); //2. Search by guest details
        }

        if (isRoomDetailsProvided(input)) {
            guestMap.putAll(filterByRoomNumber(input)); //3. Search by room number
        }

        if (guestMap.isEmpty()) {
            throw new HotelException("No matching guests found for the provided criteria.");
        }

        List<GuestOutput> values = new ArrayList<>(guestMap.values());
        GetReportOutput output = GetReportOutput.builder()
                .guests(values)
                .build();

        log.info("Ended getRoomReport with output: {}", output);
        return output;
    }

    private Boolean isAnyFieldProvided(GetReportInput input) {
        return input.getStartDate() != null ||
                input.getEndDate() != null ||
                input.getFirstName() != null ||
                input.getLastName() != null ||
                input.getPhoneNumber() != null ||
                input.getIdCardNumber() != null ||
                input.getIdCardValidity() != null ||
                input.getIdCardIssueAuthority() != null ||
                input.getIdCardIssueDate() != null ||
                input.getRoomNumber() != null;
    }

    private Boolean isDateRangeProvided(GetReportInput input) {
        return input.getStartDate() != null && input.getEndDate() != null;
    }

    private Boolean isGuestDetailsProvided(GetReportInput input) {
        return input.getFirstName() != null &&
                input.getLastName() != null &&
                input.getPhoneNumber() != null &&
                input.getIdCardNumber() != null &&
                input.getIdCardValidity() != null &&
                input.getIdCardIssueAuthority() != null &&
                input.getIdCardIssueDate() != null;
    }

    private Boolean isRoomDetailsProvided(GetReportInput input) {
        return input.getRoomNumber() != null;
    }

    private Map<UUID, GuestOutput> filterByRoomNumber(GetReportInput input) {
        Map<UUID, GuestOutput> guestMap = new HashMap<>();

        Room room = roomRepository.findByRoomNumber(input.getRoomNumber())
                .orElseThrow(() -> new HotelException("No room found with number: " + input.getRoomNumber()));

        List<Booking> bookings = bookingRepository.findByRoomId(room.getId())
                .orElse(Collections.emptyList());

        for (Booking booking : bookings) {
            for (Guest guest : booking.getGuests()) {
                if (!guestMap.containsKey(guest.getId())) {
                    GuestOutput guestOutput =
                            conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                    .startDate(booking.getStartDate())
                                    .endDate(booking.getEndDate())
                                    .build();
                    guestMap.put(guest.getId(), guestOutput);
                }
            }
        }

        return guestMap;
    }

    private Map<UUID, GuestOutput> filterByDateRange(GetReportInput input) {
        Map<UUID, GuestOutput> guestMap = new HashMap<>();

        List<Booking> bookings = bookingRepository
                .findByDateRange(LocalDate.parse(input.getStartDate()), LocalDate.parse(input.getEndDate()))
                .orElse(Collections.emptyList());

        for (Booking booking : bookings) {
            for (Guest guest : booking.getGuests()) {
                GuestOutput guestOutput =
                        conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                .startDate(booking.getStartDate())
                                .endDate(booking.getEndDate())
                                .build();
                guestMap.putIfAbsent(guest.getId(), guestOutput);
            }
        }

        return guestMap;
    }

    private Map<UUID, GuestOutput> filterByGuestDetails(GetReportInput input) {
        Map<UUID, GuestOutput> guestMap = new HashMap<>();

        List<Guest> matchingGuests = guestRepository.findMatchingGuests(
                input.getFirstName(),
                input.getLastName(),
                input.getPhoneNumber(),
                input.getIdCardNumber(),
                input.getIdCardValidity(),
                input.getIdCardIssueAuthority(),
                input.getIdCardIssueDate()
        ).orElse(Collections.emptyList());

        for (Guest guest : matchingGuests) {
            List<Booking> bookings = bookingRepository.findByGuestIdCardNumber(guest.getIdCardNumber())
                    .orElse(Collections.emptyList());

            if (bookings.isEmpty()) {
                if (!guestMap.containsKey(guest.getId())) {
                    GuestOutput guestOutput = conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class).build();
                    guestMap.put(guest.getId(), guestOutput);
                }
            } else {
                for (Booking booking : bookings) {
                    if (!guestMap.containsKey(guest.getId())) {
                        GuestOutput guestOutput =
                                conversionService.convert(guest, GuestOutput.GuestOutputBuilder.class)
                                        .startDate(booking.getStartDate())
                                        .endDate(booking.getEndDate())
                                        .build();
                        guestMap.put(guest.getId(), guestOutput);
                    }
                }
            }

        }

        return guestMap;
    }
}
