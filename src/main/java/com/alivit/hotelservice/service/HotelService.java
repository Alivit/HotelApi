package com.alivit.hotelservice.service;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;

public interface HotelService {

    HotelCreateResponse save(HotelCreateRequest hotelCreateRequest);

    HotelFindResponse findById(Long id);
}
