package com.tinqinacademy.hotel.core.converters.system.registerguest;

import com.tinqinacademy.hotel.api.operations.system.registerguest.GuestInput;
import com.tinqinacademy.hotel.persistence.model.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GuestInputToGuest implements Converter<GuestInput, Guest> {
    @Override
    public Guest convert(GuestInput source) {
        log.info("Started Converter - GuestInput to Guest");

        Guest target = Guest.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .idCardNumber(source.getIdCardNumber())
                .idCardValidity(source.getIdCardValidity())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .birthdate(source.getBirthdate())
                .build();

        log.info("Ended Converter - GuestInput to Guest");
        return target;
    }
}
