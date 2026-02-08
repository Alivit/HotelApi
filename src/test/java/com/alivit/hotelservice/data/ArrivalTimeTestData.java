package com.alivit.hotelservice.data;

import com.alivit.hotelservice.dto.ArrivalTimeDto;
import com.alivit.hotelservice.model.ArrivalTime;

import java.time.LocalTime;

public final class ArrivalTimeTestData {

    private static final Long ID = 1L;
    private static final LocalTime CHECK_IN = LocalTime.parse("14:00");
    private static final LocalTime CHECK_OUT = LocalTime.parse("12:00");

    private ArrivalTimeTestData() {

    }

    public static ArrivalTimeDto getArrivalTimeDto() {
        return ArrivalTimeDto.builder()
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .build();
    }

    public static ArrivalTime getArrivalTime() {
        return ArrivalTime.builder()
                .id(ID)
                .checkIn(CHECK_IN)
                .checkOut(CHECK_OUT)
                .build();
    }
}
