package com.alivit.hotelservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record HotelCreateRequest(

        @NotBlank
        String name,
        String description,

        @NotBlank
        String brand,

        @Valid
        @NotNull
        AddressDto address,

        @Valid
        @NotNull
        ContactsDto contacts,

        @Valid
        @NotNull
        ArrivalTimeDto arrivalTime
) {
}
