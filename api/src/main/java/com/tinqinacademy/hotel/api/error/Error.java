package com.tinqinacademy.hotel.api.error;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Error {
    private String field; // for MethodArgumentNotValidException
    private String message;
}
