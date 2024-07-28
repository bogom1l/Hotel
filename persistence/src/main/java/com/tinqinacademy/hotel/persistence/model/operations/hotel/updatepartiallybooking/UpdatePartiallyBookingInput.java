package com.tinqinacademy.hotel.persistence.model.operations.hotel.updatepartiallybooking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.persistence.model.Guest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UpdatePartiallyBookingInput {
    @JsonIgnore
    private String id;

    private String roomNumber;

    // no userId, because: user shouldn't be changed; instead every user creates his new booking

    private String startDate;

    private String endDate;

    @Positive
    private BigDecimal totalPrice;

    @Valid
    private List<UpdatePartiallyGuestInput> guests;
}