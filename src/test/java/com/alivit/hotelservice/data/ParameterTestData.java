package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.ParamsDto;

public final class ParameterTestData {
    private final static String NAME = "DoubleTree";
    private final static String BRAND = "Hilton";
    private final static String CITY = "Minsk";
    private final static String COUNTRY = "Belarus";
    private final static String AMENITIES = "Free parking";

    private ParameterTestData() {}

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
