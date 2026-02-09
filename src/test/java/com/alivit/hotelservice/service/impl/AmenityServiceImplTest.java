package com.alivit.hotelservice.service.impl;

import com.alivit.hotelservice.handler.exception.ResourceNotFoundException;
import com.alivit.hotelservice.repository.AmenityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.alivit.hotelservice.handler.exception.ExceptionAnswer.HOTEL_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AmenityServiceImplTest {

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private AmenityServiceImpl amenityService;

    @Test
    void saveShouldThrowResourceNotFoundException() {
        Long nonExistentHotelId = 999L;
        Set<String> amenities = Set.of("Free WiFi", "Fitness center");

        when(amenityRepository.existsHotelById(nonExistentHotelId)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> amenityService.save(amenities, nonExistentHotelId)
        );

        assertEquals(String.format(HOTEL_NOT_FOUND, nonExistentHotelId), exception.getMessage());

        verify(amenityRepository, never()).getAmenitiesNative(anyLong());
        verify(amenityRepository, never()).insertAmenity(anyLong(), anyString());
    }

    @Test
    void saveShouldNotInsertDuplicateAmenities() {
        Long hotelId = 1L;
        Set<String> existingAmenities = Set.of("Free parking", "Free WiFi", "Non-smoking rooms");
        Set<String> newAmenities = Set.of("Free parking", "Free WiFi", "Non-smoking rooms", "Concierge", "On-site restaurant");

        when(amenityRepository.existsHotelById(hotelId)).thenReturn(true);
        when(amenityRepository.getAmenitiesNative(hotelId)).thenReturn(existingAmenities);

        amenityService.save(newAmenities, hotelId);

        verify(amenityRepository, times(1)).insertAmenity(hotelId, "Concierge");
        verify(amenityRepository, times(1)).insertAmenity(hotelId, "On-site restaurant");
        verify(amenityRepository, never()).insertAmenity(hotelId, "Free WiFi");
        verify(amenityRepository, never()).insertAmenity(hotelId, "Free parking");
    }
}
