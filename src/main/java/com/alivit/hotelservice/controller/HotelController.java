package com.alivit.hotelservice.controller;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/hotels")
    public HotelCreateResponse save(@RequestBody HotelCreateRequest hotelCreateRequest){
        return hotelService.save(hotelCreateRequest);
    }
}
