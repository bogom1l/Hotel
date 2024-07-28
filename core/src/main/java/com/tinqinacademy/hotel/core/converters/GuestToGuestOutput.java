package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GuestOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuestToGuestOutput implements Converter<Guest, GuestOutput> {
    @Override
    public GuestOutput convert(Guest source) {
        log.info("Started Converter - Guest to GuestOutput");

        GuestOutput guestOutput = GuestOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNo(source.getPhoneNumber())
                .idCardNo(source.getIdCardNumber())
                .idCardValidity(source.getIdCardValidity())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .build();

        log.info("Ended Converter - Guest to GuestOutput");
        return guestOutput;
    }
}
