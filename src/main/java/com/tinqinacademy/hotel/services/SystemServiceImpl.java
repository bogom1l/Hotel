package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.output.RegisterVisitorOutput;
import com.tinqinacademy.hotel.services.contracts.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("registerVisitor called with input: {}", input);

        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

        log.info("registerVisitor output: {}", output);
        return output;
    }

}
