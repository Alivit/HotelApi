package com.alivit.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;

public record ArrivalTimeDto(
        @NotBlank
        LocalTime checkIn,
        LocalTime checkOut
) {
}
