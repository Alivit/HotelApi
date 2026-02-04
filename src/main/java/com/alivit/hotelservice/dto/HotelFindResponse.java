package com.alivit.hotelservice.dto;

import java.util.List;

public record HotelFindResponse(
        Long id,
        String name,
        String description,
        String brand,
        AddressDto address,
        ContactsDto contacts,
        ArrivalTimeDto arrivalTime,
        List<String> amenities
) {
}
