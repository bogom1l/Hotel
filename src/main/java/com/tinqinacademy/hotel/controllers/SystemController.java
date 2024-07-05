package com.tinqinacademy.hotel.controllers;

import com.tinqinacademy.hotel.model.input.RegisterReportInput;
import com.tinqinacademy.hotel.model.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.model.output.RegisterReportOutput;
import com.tinqinacademy.hotel.model.output.RegisterVisitorOutput;
import com.tinqinacademy.hotel.services.contracts.SystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system")
public class SystemController {

    private final SystemService systemService;

    @Autowired
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    @Operation(summary = "Register a visitor", description = "Registers a visitor as room renter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Visitor registered successfully"),
            @ApiResponse(responseCode = "400", description = "Error registering visitor")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerVisitor(@RequestBody RegisterVisitorInput input) {

        RegisterVisitorOutput result = systemService.registerVisitor(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/register")
    public ResponseEntity<?> register(@RequestParam String startDate,
                                      @RequestParam String endDate,
                                      @RequestParam String firstName,
                                      @RequestParam String lastName,
                                      @RequestParam String phoneNo,
                                      @RequestParam String idCardNo,
                                      @RequestParam String idCardValidity,
                                      @RequestParam String idCardIssueAuthority,
                                      @RequestParam String idCardIssueDate,
                                      @RequestParam String roomNo) {

        RegisterReportInput input = RegisterReportInput.builder()
                .startDate(startDate)
                .endDate(endDate)
                .fistName(firstName)
                .lastName(lastName)
                .phoneNo(phoneNo)
                .idCardNo(idCardNo)
                .idCardValidity(idCardValidity)
                .idCardIssueAuthority(idCardIssueAuthority)
                .idCardIssueDate(idCardIssueDate)
                .roomNo(roomNo)
                .build();

        RegisterReportOutput result = systemService.registerReport(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
