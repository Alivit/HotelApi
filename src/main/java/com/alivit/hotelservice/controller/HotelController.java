package com.alivit.hotelservice.controller;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;
import com.alivit.hotelservice.dto.ParamsDto;
import com.alivit.hotelservice.service.HotelService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("property-view")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping("/hotels")
    public HotelCreateResponse save(@RequestBody HotelCreateRequest hotelCreateRequest){
        log.debug("Saving hotel {}", hotelCreateRequest);
        return hotelService.save(hotelCreateRequest);
    }

    @GetMapping("/hotels")
    public Page<HotelCreateResponse> findAll(
            @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) int size,
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int page){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return hotelService.findAll(pageable);
    }

    @GetMapping("/search")
    public Page<HotelCreateResponse> findByParams(
            @RequestParam(name = "size", defaultValue = "10") @Min(value = 1) int size,
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int page,
            ParamsDto paramsDto){
        log.debug("Getting hotels by params: {}", paramsDto);
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return hotelService.findByParams(pageable, paramsDto);
    }

    @GetMapping("/hotels/{id}")
    public HotelFindResponse findById(@PathVariable(value = "id") Long id){
        log.debug("Getting hotel by id {}", id);
        return hotelService.findById(id);
    }
}
