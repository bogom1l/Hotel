package com.tinqinacademy.hotel.core.converters.hotel.getbookinghistory;

import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryBookingOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryGuestOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryRoomOutput;
import com.tinqinacademy.hotel.persistence.model.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingToGetBookingHistoryBookingOutput implements Converter<Booking, GetBookingHistoryBookingOutput> {
    private final ConversionService conversionService;

    @Lazy
    public BookingToGetBookingHistoryBookingOutput(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public GetBookingHistoryBookingOutput convert(Booking source) {
        log.info("Started Converter - Booking to GetBookingOutput");

        GetBookingHistoryBookingOutput target = GetBookingHistoryBookingOutput.builder()
                .room(conversionService.convert(source.getRoom(), GetBookingHistoryRoomOutput.class))
                .bookingStartDate(source.getStartDate())
                .bookingEndDate(source.getEndDate())
                .bookingTotalPrice(source.getTotalPrice())
                .guests(source.getGuests().stream()
                        .map(guest -> conversionService.convert(guest, GetBookingHistoryGuestOutput.class))
                        .collect(Collectors.toSet()))
                .build();

        log.info("Ended Converter - Booking to GetBookingOutput");
        return target;
    }
}
