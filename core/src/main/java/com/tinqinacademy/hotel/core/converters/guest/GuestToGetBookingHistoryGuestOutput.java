package com.tinqinacademy.hotel.core.converters.guest;

import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryGuestOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GuestToGetBookingHistoryGuestOutput implements Converter<Guest, GetBookingHistoryGuestOutput> {
    @Override
    public GetBookingHistoryGuestOutput convert(Guest source) {
        log.info("Started Converter - Guest to GetBookingGuestOutput");

        GetBookingHistoryGuestOutput getBookingHistoryGuestOutput = GetBookingHistoryGuestOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .build();

        log.info("Ended Converter - Guest to GetBookingGuestOutput");
        return getBookingHistoryGuestOutput;
    }
}
