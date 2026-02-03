package com.alivit.hotelservice.service.impl;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.mapper.HotelMapper;
import com.alivit.hotelservice.model.Hotel;
import com.alivit.hotelservice.repository.HotelRepository;
import com.alivit.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public HotelCreateResponse save(HotelCreateRequest hotelCreateRequest) {
        Hotel hotel = hotelMapper.hotelCreateRequestToHotel(hotelCreateRequest);
        hotel = hotelRepository.save(hotel);
        return hotelMapper.hotelToHotelCreateResponse(hotel);
    }
}
