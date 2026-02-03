package com.alivit.hotelservice.dto;

public record HotelCreateResponse(
        Long id,
        String name,
        String description,
        String address,
        String phone
) {
}
