package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.persistence.model.Guest;
import com.tinqinacademy.hotel.persistence.model.operations.system.getreport.GuestOutput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GuestToGuestOutputConverter implements Converter<Guest, GuestOutput> {
    @Override
    public GuestOutput convert(Guest source) {
        return null;//todo
    }
}
