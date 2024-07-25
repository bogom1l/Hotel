package com.tinqinacademy.hotel.core;

import com.tinqinacademy.hotel.api.error.HotelException;
import com.tinqinacademy.hotel.core.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.model.Booking;
import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.Room;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GetReportOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.getroomreport.GuestOutput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.persistence.model.operations.system.registerguest.RegisterGuestOutput;
import com.tinqinacademy.hotel.persistence.repository.BookingRepository;
import com.tinqinacademy.hotel.persistence.repository.GuestRepository;
import com.tinqinacademy.hotel.persistence.repository.RoomRepository;
import com.tinqinacademy.hotel.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public SystemServiceImpl(RoomRepository roomRepository, GuestRepository guestRepository, BookingRepository bookingRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Start registerGuest with input: {}", input);

        for (GuestInput guestInput : input.getGuests()) {
            Room room = roomRepository.findById(UUID.fromString(guestInput.getRoomId()))
                    .orElseThrow(() -> new HotelException("no room found"));

            Guest guest = Guest.builder()
                    .firstName(guestInput.getFirstName())
                    .lastName(guestInput.getLastName())
                    .phoneNumber(guestInput.getPhoneNo())
                    .idCardNumber(guestInput.getIdCardNo())
                    .idCardValidity(guestInput.getIdCardValidity())
                    .idCardIssueAuthority(guestInput.getIdCardIssueAuthority())
                    .idCardIssueDate(guestInput.getIdCardIssueDate())
                    .birthdate(guestInput.getBirthdate())
                    .build();

            Booking booking = bookingRepository.findByRoomIdAndStartDateAndEndDate(
                            room.getId(), guestInput.getStartDate(), guestInput.getEndDate())
                    .orElseThrow(() -> new HotelException("no booking found"));

            booking.getGuests().add(guest);

            guestRepository.save(guest);
            bookingRepository.save(booking);
        }

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("End registerGuest with output: {}", output);
        return output;
    }

    @Override
    public GetReportOutput getReport(GetReportInput input) {
        log.info("Start getRoomReport with input: {}", input);

        LocalDate startDate = LocalDate.parse(input.getStartDate());
        LocalDate endDate = LocalDate.parse(input.getEndDate());

        Room room = roomRepository.findByRoomNumber(input.getRoomNo())
                .orElseThrow(() -> new HotelException("No room found with number: " + input.getRoomNo()));

        // find all existing bookings that match: roomId, startDate, endDate
        List<Booking> bookings = bookingRepository.findByRoomIdAndDateRange(
                        room.getId(), startDate, endDate)
                .orElseThrow(() -> new HotelException("no bookings found"));

        // find all existing guests that match: firstName, lastName, phoneNo,idCardNo, idCardValidity, idCardIssueAuthority, idCardIssueDate
        List<Guest> matchingGuests = guestRepository.findMatchingGuests(
                        input.getFirstName(),
                        input.getLastName(),
                        input.getPhoneNo(),
                        input.getIdCardNo(),
                        input.getIdCardValidity(),
                        input.getIdCardIssueAuthority(),
                        input.getIdCardIssueDate()
                )
                .orElseThrow(() -> new HotelException("no guests found"));

        List<GuestOutput> guestOutputs = new ArrayList<>();

        // iterate through the bookings and through the guests, and find if any booking contains one of the matching guests
        for (Booking booking : bookings) {
            for (Guest guest : matchingGuests) {
                if (booking.getGuests().contains(guest)) {

                    GuestOutput visitorOutput = GuestOutput.builder()
                            .startDate(booking.getStartDate())
                            .endDate(booking.getEndDate())
                            .firstName(guest.getFirstName())
                            .lastName(guest.getLastName())
                            .phoneNo(guest.getPhoneNumber())
                            .idCardNo(guest.getIdCardNumber())
                            .idCardValidity(guest.getIdCardValidity())
                            .idCardIssueAuthority(guest.getIdCardIssueAuthority())
                            .idCardIssueDate(guest.getIdCardIssueDate())
                            .build();
                    guestOutputs.add(visitorOutput);
                }
            }
        }

        if (guestOutputs.isEmpty()) {
            throw new HotelException("No matching guests found for the provided criteria.");
        }

        GetReportOutput output = GetReportOutput.builder()
                .guests(guestOutputs)
                .build();

        log.info("End getRoomReport with output: {}", output);
        return output;
    }



    /*
    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor with input: {}", input);

        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

        log.info("End registerVisitor with output: {}", output);
        return output;
    }

    @Override
    public RegisterReportOutput registerReport(RegisterReportInput input) {
        log.info("Start registerReport with input: {}", input);

        List<VisitorReportOutput> sampleVisitorReports = generateSampleVisitorReports();

        RegisterReportOutput output = RegisterReportOutput.builder()
                .visitors(sampleVisitorReports)
                .build();

        log.info("End registerReport with output: {}", output);
        return output;
    }

    private List<VisitorReportOutput> generateSampleVisitorReports() {
        List<VisitorReportOutput> sampleVisitorReports = new ArrayList<>();

        VisitorReportOutput v1 = VisitorReportOutput.builder()
                .firstName("firstname1").lastName("lastname1")
                .startDate(LocalDate.parse("2024-11-02"))
                .endDate(LocalDate.parse("2024-05-05"))
                .phoneNo("+359123123")
                .idCardNo("1")
                .idCardValidity("2")
                .idCardIssueAuthority("3")
                .idCardIssueDate("4")
                .build();

        VisitorReportOutput v2 = VisitorReportOutput.builder()
                .firstName("fn2").lastName("ln2")
                .startDate(LocalDate.parse("2022-12-01"))
                .endDate(LocalDate.parse("2023-02-02"))
                .phoneNo("+3591444").idCardNo("44")
                .idCardValidity("55")
                .idCardIssueAuthority("66")
                .idCardIssueDate("77")
                .build();

        sampleVisitorReports.add(v1);
        sampleVisitorReports.add(v2);

        return sampleVisitorReports;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom with input: {}", input);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .id("1")
                .build();

        // randomly sometimes throw an error just for testing purposes
        if (new Random().nextBoolean()) {
            throw new HotelException("Random generated HotelError for testing purposes - bad request - error creating room");
        }

        log.info("End createRoom with output: {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom with input: {}", input);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .id(input.getRoomId())
                .build();

        log.info("End updateRoom with output: {}", output);
        return output;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom with input: {}", input);

        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .id(input.getRoomId())
                .build();

        log.info("End partialUpdateRoom with output: {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom with input: {}", input);

        DeleteRoomOutput output = DeleteRoomOutput.builder().build();

        log.info("End deleteRoom with output: {}", output);
        return output;
    }

    */


}