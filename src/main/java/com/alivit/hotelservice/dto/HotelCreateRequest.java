package com.alivit.hotelservice.dto;

import com.alivit.hotelservice.model.Address;
import com.alivit.hotelservice.model.ArrivalTime;
import com.alivit.hotelservice.model.Contact;

public record HotelCreateRequest(
        String name,
        String description,
        String brand,
        Address address,
        Contact contacts,
        ArrivalTime arrivalTime
) {
}
