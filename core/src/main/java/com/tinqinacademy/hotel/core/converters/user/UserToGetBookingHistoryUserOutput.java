package com.tinqinacademy.hotel.core.converters.user;

import com.tinqinacademy.hotel.api.operations.hotel.getbookinghistory.GetBookingHistoryUserOutput;
import com.tinqinacademy.hotel.persistence.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserToGetBookingHistoryUserOutput implements Converter<User, GetBookingHistoryUserOutput> {
    @Override
    public GetBookingHistoryUserOutput convert(User source) {
        log.info("Started Converter - User to GetBookingUserOutput");

        GetBookingHistoryUserOutput getBookingHistoryUserOutput = GetBookingHistoryUserOutput.builder()
                .userEmail(source.getEmail())
                .userPhoneNumber(source.getPhoneNumber())
                .build();

        log.info("Ended Converter - User to GetBookingUserOutput");
        return getBookingHistoryUserOutput;
    }
}
