package com.tinqinacademy.hotel.model.operations.deletebooking;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DeleteBookingInput {
    private String bookingId;
}
