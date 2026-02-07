package com.alivit.hotelservice.service;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;
import com.alivit.hotelservice.dto.ParamsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface HotelService {

    HotelCreateResponse save(HotelCreateRequest hotelCreateRequest);

    HotelFindResponse findById(Long id);

    Page<HotelCreateResponse> findAll(Pageable pageable);

    Page<HotelCreateResponse> findByParams(Pageable pageable, ParamsDto paramsDto);

    Map<String, Long> getHistogram(String param);
}
