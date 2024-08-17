package com.tinqinacademy.hotel.core.converters.hotel.updatepartiallybooking;

import com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking.UpdatePartiallyBookingOutput;
import com.tinqinacademy.hotel.persistence.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingToUpdatePartiallyBookingOutput implements Converter<Booking, UpdatePartiallyBookingOutput> {
    @Override
    public UpdatePartiallyBookingOutput convert(Booking source) {
        log.info("Started Converter - Booking to UpdatePartiallyBookingOutput");

        UpdatePartiallyBookingOutput updatePartiallyBookingOutput = UpdatePartiallyBookingOutput
                .builder()
                .id((source.getId()))
                .build();

        log.info("Ended Converter - Booking to UpdatePartiallyBookingOutput");
        return updatePartiallyBookingOutput;
    }
}
