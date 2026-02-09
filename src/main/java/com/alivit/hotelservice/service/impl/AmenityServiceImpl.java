package com.alivit.hotelservice.service.impl;

import com.alivit.hotelservice.handler.exception.ResourceNotFoundException;
import com.alivit.hotelservice.repository.AmenityRepository;
import com.alivit.hotelservice.service.AmenityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.alivit.hotelservice.handler.exception.ExceptionAnswer.HOTEL_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;

    @Override
    @Transactional
    public void save(Set<String> amenities, Long id) {
        Set<String> amenitiesCopy = new HashSet<>(amenities);
        if (!amenityRepository.existsHotelById((id))) {
            log.error(String.format(HOTEL_NOT_FOUND, id));
            throw new ResourceNotFoundException(String.format(HOTEL_NOT_FOUND, id));
        }

        Set<String> oldAmenities = amenityRepository.getAmenitiesNative(id);
        if (oldAmenities != null) {
            amenitiesCopy.removeAll(oldAmenities);
        }
        amenitiesCopy.forEach(amenity -> amenityRepository.insertAmenity(id, amenity));
        log.debug("The following amenities - {} have been added to the hotel", amenities);
    }
}
