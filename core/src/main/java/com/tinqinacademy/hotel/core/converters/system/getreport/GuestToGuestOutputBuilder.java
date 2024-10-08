package com.tinqinacademy.hotel.core.converters.system.getreport;

import com.tinqinacademy.hotel.api.operations.system.getreport.GuestOutput;
import com.tinqinacademy.hotel.persistence.model.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuestToGuestOutputBuilder implements Converter<Guest, GuestOutput.GuestOutputBuilder> {
    @Override
    public GuestOutput.GuestOutputBuilder convert(Guest source) {
        log.info("Started Converter - Guest to GuestOutput");

        GuestOutput.GuestOutputBuilder target = GuestOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .idCardNumber(source.getIdCardNumber())
                .idCardValidity(source.getIdCardValidity())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate());

        log.info("Ended Converter - Guest to GuestOutput");
        return target;
    }
}
