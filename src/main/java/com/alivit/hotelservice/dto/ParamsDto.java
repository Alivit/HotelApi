package com.alivit.hotelservice.dto;

import lombok.Builder;

@Builder
public record ParamsDto(
        String name,
        String brand,
        String city,
        String country,
        String amenities
) {
}
