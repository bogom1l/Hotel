package com.tinqinacademy.hotel.core.converters.guest;

import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyGuestInput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdatePartiallyGuestInputToGuest implements Converter<UpdatePartiallyGuestInput, Guest> {

    @Override
    public Guest convert(UpdatePartiallyGuestInput source) {
        return Guest.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .birthdate(source.getBirthdate())
                .idCardNumber(source.getIdCardNumber())
                .idCardIssueAuthority(source.getIdCardIssueAuthority())
                .idCardIssueDate(source.getIdCardIssueDate())
                .idCardValidity(source.getIdCardValidity())
                .build();
    }
}