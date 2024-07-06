package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.getroomreport.RegisterReportInput;
import com.tinqinacademy.hotel.model.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.getroomreport.VisitorReportOutput;
import com.tinqinacademy.hotel.model.getroomreport.RegisterReportOutput;
import com.tinqinacademy.hotel.model.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.services.contracts.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private List<VisitorReportOutput> generateSampleVisitorReports(){
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

}
