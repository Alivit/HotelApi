package com.alivit.hotelservice.service.impl;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;
import com.alivit.hotelservice.dto.ParamsDto;
import com.alivit.hotelservice.handler.exception.ResourceNotCreatedException;
import com.alivit.hotelservice.handler.exception.ResourceNotFoundException;
import com.alivit.hotelservice.mapper.HotelMapper;
import com.alivit.hotelservice.model.Hotel;
import com.alivit.hotelservice.repository.HotelRepository;
import com.alivit.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.alivit.hotelservice.handler.exception.ExceptionAnswer.HOTEL_NOT_CREATED;
import static com.alivit.hotelservice.handler.exception.ExceptionAnswer.HOTEL_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    @Transactional
    public HotelCreateResponse save(HotelCreateRequest hotelCreateRequest) {
        Hotel hotelBeforeSaving = hotelMapper.hotelCreateRequestToHotel(hotelCreateRequest);
        Hotel savedHotel;
        try {
            savedHotel = hotelRepository.save(hotelBeforeSaving);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceNotCreatedException(String.format(HOTEL_NOT_CREATED, ex.getMessage()));
        }

        log.debug("Hotel has been created: {}", savedHotel);
        HotelCreateResponse hotelCreateResponse = hotelMapper.hotelToHotelCreateResponse(savedHotel);
        log.debug("HotelResponse dto: {}", hotelCreateResponse);
        return hotelCreateResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public HotelFindResponse findById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(String.format(HOTEL_NOT_FOUND, id));
                    return new ResourceNotFoundException(String.format(HOTEL_NOT_FOUND, id));
                });
        log.debug("Hotel has been find by id: {} \n Hotel: {}", id, hotel);
        HotelFindResponse hotelFindResponse = hotelMapper.hotelToHotelFindResponse(hotel);
        log.debug("HotelResponse dto: {}", hotelFindResponse);
        return hotelFindResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelCreateResponse> findAll(Pageable pageable) {
        return hotelRepository.findAll(pageable).map(hotelMapper::hotelToHotelCreateResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HotelCreateResponse> findByParams(Pageable pageable, ParamsDto paramsDto) {
        return hotelRepository.findByParams(paramsDto, pageable).map(hotelMapper::hotelToHotelCreateResponse);
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        return switch (param) {
            case "country" -> listToMap(hotelRepository.groupByCountry());
            case "city" -> listToMap(hotelRepository.groupByCity());
            case "amenities" -> listToMap(hotelRepository.groupByAmenity());
            case "brand" -> listToMap(hotelRepository.groupByBrand());
            default -> Map.of();
        };
    }

    private Map<String, Long> listToMap(List<Object[]> list) {
        return list.stream().collect(Collectors.toMap(
                obj -> (String) obj[0],
                obj -> (Long) obj[1]
        ));
    }
}
