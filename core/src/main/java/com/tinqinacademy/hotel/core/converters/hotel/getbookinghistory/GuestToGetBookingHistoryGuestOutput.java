package com.tinqinacademy.hotel.core.converters.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryGuestOutput;
import com.tinqinacademy.hotel.persistence.model.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GuestToGetBookingHistoryGuestOutput implements Converter<Guest, GetBookingHistoryGuestOutput> {
    @Override
    public GetBookingHistoryGuestOutput convert(Guest source) {
        log.info("Started Converter - Guest to GetBookingGuestOutput");

        GetBookingHistoryGuestOutput target = GetBookingHistoryGuestOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .birthdate(source.getBirthdate())
                .build();

        log.info("Ended Converter - Guest to GetBookingGuestOutput");
        return target;
    }
}
