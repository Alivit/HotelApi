package com.alivit.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record HotelCreateRequest(

        @NotBlank
        String name,
        String description,

        @NotBlank
        String brand,

        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime
) {
}
