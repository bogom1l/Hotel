package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.model.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.operations.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.operations.getroomreport.VisitorReportOutput;
import com.tinqinacademy.hotel.model.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.model.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.model.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.services.contracts.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor with input: {}", input);

        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();

        log.info("End registerVisitor with output: {}", output);
        return output;
    }

    @Override
    public RegisterReportOutput registerReport(RegisterReportInput input) {
        log.info("Start registerReport with input: {}", input);

        List<VisitorReportOutput> sampleVisitorReports = generateSampleVisitorReports();

        RegisterReportOutput output = RegisterReportOutput.builder()
                .visitors(sampleVisitorReports)
                .build();

        log.info("End registerReport with output: {}", output);
        return output;
    }

    private List<VisitorReportOutput> generateSampleVisitorReports() {
        List<VisitorReportOutput> sampleVisitorReports = new ArrayList<>();

        VisitorReportOutput v1 = VisitorReportOutput.builder()
                .firstName("firstname1")
                .lastName("lastname1")
                .startDate(LocalDate.parse("2024-11-02"))
                .endDate(LocalDate.parse("2024-05-05"))
                .phoneNo("+359123123")
                .idCardNo("1")
                .idCardValidity("2")
                .idCardIssueAuthority("3")
                .idCardIssueDate("4")
                .build();

        VisitorReportOutput v2 = VisitorReportOutput.builder()
                .firstName("fn2")
                .lastName("ln2")
                .startDate(LocalDate.parse("2022-12-01"))
                .endDate(LocalDate.parse("2023-02-02"))
                .phoneNo("+3591444")
                .idCardNo("44")
                .idCardValidity("55")
                .idCardIssueAuthority("66")
                .idCardIssueDate("77")
                .build();

        sampleVisitorReports.add(v1);
        sampleVisitorReports.add(v2);

        return sampleVisitorReports;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom with input: {}", input);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .id("1")
                .build();

        log.info("End createRoom with output: {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom with input: {}", input);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .id("1")//.id(input.getRoomId())
                .build();

        log.info("End updateRoom with output: {}", output);
        return output;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom with input: {}", input);

        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .id("1")//.id(input.getRoomId())
                .build();

        log.info("End partialUpdateRoom with output: {}", output);
        return output;
    }
}
