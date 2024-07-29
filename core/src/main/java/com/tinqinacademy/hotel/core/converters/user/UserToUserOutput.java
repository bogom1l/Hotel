package com.tinqinacademy.hotel.core.converters.user;

import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.model.operations.system.getallusers.UserOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserToUserOutput implements Converter<User, UserOutput> {
    @Override
    public UserOutput convert(User source) {
        log.info("Started Converter - User to UserOutput");

        UserOutput userOutput = UserOutput.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .email(source.getEmail())
                .phoneNumber(source.getPhoneNumber())
                .birthdate(source.getBirthdate())
                .build();

        log.info("Ended Converter - User to UserOutput");
        return userOutput;
    }
}
