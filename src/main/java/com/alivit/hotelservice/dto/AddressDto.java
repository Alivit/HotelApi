package com.alivit.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressDto(

        @NotBlank
        String houseNumber,

        @NotBlank
        String street,

        @NotBlank
        String city,

        @NotBlank
        String country,

        @NotBlank
        String postCode
) {
}
