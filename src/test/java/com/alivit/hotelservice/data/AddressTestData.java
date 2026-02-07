package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.AddressDto;
import com.alivit.hotelservice.model.Address;

public final class AddressTestData {

    private static final Long ID = 1L;
    private static final String HOUSE_NUMBER = "9";
    private static final String STREET = "Pobediteley Avenue";
    private static final String CITY = "Minsk";
    private static final String COUNTRY = "Belarus";
    private static final String POST_CODE = "220004";

    private AddressTestData() {}

    public static AddressDto getAddressDto(){
        return AddressDto.builder()
                .houseNumber(HOUSE_NUMBER)
                .street(STREET)
                .city(CITY)
                .country(COUNTRY)
                .postCode(POST_CODE)
                .build();
    }

    public static Address getAddress(){
        return Address.builder()
                .id(ID)
                .houseNumber(HOUSE_NUMBER)
                .street(STREET)
                .city(CITY)
                .country(COUNTRY)
                .postCode(POST_CODE)
                .build();
    }
}