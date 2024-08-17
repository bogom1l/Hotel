package com.tinqinacademy.hotel.core.converters.hotel.bookroom;

import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.persistence.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class BookRoomInputToBookingBuilder implements Converter<BookRoomInput, Booking.BookingBuilder> {
    @Override
    public Booking.BookingBuilder convert(BookRoomInput source) {
        log.info("Started Converter - BookRoomInput to Booking");

        Booking.BookingBuilder target = Booking.builder()
                .userId(UUID.fromString(source.getUserId()))
                .startDate(source.getStartDate())
                .endDate(source.getEndDate());

        log.info("Ended Converter - BookRoomInput to Booking");
        return target;
    }
}
