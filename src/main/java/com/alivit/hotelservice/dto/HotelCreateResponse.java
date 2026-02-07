package com.alivit.hotelservice.dto;

import lombok.Builder;

@Builder
public record HotelCreateResponse(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
