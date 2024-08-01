package com.tinqinacademy.hotel.api.operations.hotel.updatepartiallybooking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.hotel.api.base.OperationInput;
import com.tinqinacademy.hotel.api.validation.room.RoomNumberValidation;
import jakarta.validation.Valid;
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
public class UpdatePartiallyBookingInput implements OperationInput {
    @JsonIgnore
    private String bookingId;

    @RoomNumberValidation(optional = true) // Custom validation
    private String roomNumber;

    // no userId, because: user shouldn't be changed; instead every user creates his new booking

    private String startDate;

    private String endDate;

    @Positive
    private BigDecimal totalPrice;

    private List<@Valid UpdatePartiallyGuestInput> guests;
}
