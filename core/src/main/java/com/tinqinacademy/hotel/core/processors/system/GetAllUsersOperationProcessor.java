package com.tinqinacademy.hotel.core.processors.system;

import com.tinqinacademy.hotel.api.error.ErrorHandler;
import com.tinqinacademy.hotel.api.error.ErrorsWrapper;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersInput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOperation;
import com.tinqinacademy.hotel.api.operations.system.getallusers.GetAllUsersOutput;
import com.tinqinacademy.hotel.api.operations.system.getallusers.UserOutput;
import com.tinqinacademy.hotel.core.processors.base.BaseOperationProcessor;
import com.tinqinacademy.hotel.persistence.model.User;
import com.tinqinacademy.hotel.persistence.repository.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GetAllUsersOperationProcessor extends BaseOperationProcessor<GetAllUsersInput> implements GetAllUsersOperation {

    private final UserRepository userRepository;

    protected GetAllUsersOperationProcessor(ConversionService conversionService, ErrorHandler errorHandler, Validator validator, UserRepository userRepository) {
        super(conversionService, errorHandler, validator);
        this.userRepository = userRepository;
    }

    @Override
    public Either<ErrorsWrapper, GetAllUsersOutput> process(GetAllUsersInput input) {
        return Try.of(() -> getAllUsers(input))
                .toEither()
                .mapLeft(errorHandler::handleErrors);
    }

    private GetAllUsersOutput getAllUsers(GetAllUsersInput input) {
        log.info("Started getAllUsers with input: {}", input);

        validateInput(input);

        List<User> users = userRepository.findUsersByPartialName(input.getPartialName());

        List<UserOutput> usersOutput = users
                .stream()
                .map(user -> conversionService.convert(user, UserOutput.class))
                .toList();

        GetAllUsersOutput output = GetAllUsersOutput.builder()
                .users(usersOutput)
                .count(users.size())
                .build();

        log.info("Ended getAllUsers with output: {}", output);
        return output;
    }


}
