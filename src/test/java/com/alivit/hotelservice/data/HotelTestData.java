package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.AddressDto;
import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;
import com.alivit.hotelservice.model.Hotel;

import java.util.Set;

public final class HotelTestData {

    private static final Long ID = 1L;
    private static final String NAME = "DoubleTree by Hilton Minsk";
    private static final String DESCRIPTION = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in"
            + " the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...";
    private static final String BRAND = "Hilton";
    private static final Set<String> AMENITIES = Set.of("Free parking", "Free WiFi",
            "Non-smoking rooms", "Concierge",
            "On-site restaurant", "Fitness center",
            "Pet-friendly rooms", "Room service",
            "Business center", "Meeting rooms");

    private HotelTestData() {

    }

    public static Hotel getHotel() {
        return Hotel.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .brand(BRAND)
                .contacts(ContactsTestData.getContacts())
                .arrivalTime(ArrivalTimeTestData.getArrivalTime())
                .address(AddressTestData.getAddress())
                .amenities(AMENITIES)
                .build();
    }

    public static HotelCreateRequest getHotelCreateRequest() {
        return HotelCreateRequest.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .brand(BRAND)
                .address(AddressTestData.getAddressDto())
                .arrivalTime(ArrivalTimeTestData.getArrivalTimeDto())
                .contacts(ContactsTestData.getContactsDto())
                .build();
    }

    public static HotelFindResponse getHotelFindResponse() {
        return HotelFindResponse.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .brand(BRAND)
                .contacts(ContactsTestData.getContactsDto())
                .arrivalTime(ArrivalTimeTestData.getArrivalTimeDto())
                .address(AddressTestData.getAddressDto())
                .amenities(AMENITIES)
                .build();
    }

    public static HotelCreateResponse getHotelCreateResponse() {
        return HotelCreateResponse.builder()
                .id(ID)
                .name(NAME)
                .description(DESCRIPTION)
                .phone(ContactsTestData.getContactsDto().phone())
                .address(addressToString(AddressTestData.getAddressDto()))
                .build();
    }

    private static String addressToString(AddressDto address) {
        return address.houseNumber() + " " + address.street()
                + ", " + address.city() + ", " + address.postCode()
                + ", " + address.country();
    }
}
