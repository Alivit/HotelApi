package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.ParamsDto;

public final class ParameterTestData {

    private static final String NAME = "DoubleTree";
    private static final String BRAND = "Hilton";
    private static final String CITY = "Minsk";
    private static final String COUNTRY = "Belarus";
    private static final String AMENITIES = "Free parking";

    private ParameterTestData() {

    }

    public static ParamsDto getParamsDto() {
        return ParamsDto.builder()
                .name(NAME)
                .brand(BRAND)
                .city(CITY)
                .country(COUNTRY)
                .amenities(AMENITIES)
                .build();
    }
}
