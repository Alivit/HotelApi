package com.alivit.hotelservice.service;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;

public interface HotelService {

    HotelCreateResponse save(HotelCreateRequest hotelCreateRequest);

}
