package com.alivit.hotelservice.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record HotelFindResponse(
        Long id,
        String name,
        String description,
        String brand,
        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime,
        Set<String> amenities
) {
}
