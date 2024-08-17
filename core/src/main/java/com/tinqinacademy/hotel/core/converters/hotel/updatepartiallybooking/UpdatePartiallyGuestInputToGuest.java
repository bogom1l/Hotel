package com.tinqinacademy.hotel.core.converters.hotel.updatepartiallybooking;

import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyGuestInput;
import com.tinqinacademy.hotel.persistence.model.Guest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UpdatePartiallyGuestInputToGuest implements Converter<UpdatePartiallyGuestInput, Guest> {
    @Override
    public Guest convert(UpdatePartiallyGuestInput source) {
        log.info("Started Converter - UpdatePartiallyGuestInput to Guest");

        Guest guest = Guest.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .birthdate(source.getBirthdate())
                .idCardNumber(source.getIdCardNumber())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .idCardValidity(source.getIdCardValidity())
                .build();

        log.info("Ended Converter - UpdatePartiallyGuestInput to Guest");
        return guest;
    }
}
