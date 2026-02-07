package com.alivit.hotelservice.service.impl;

import com.alivit.hotelservice.data.HotelTestData;
import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.dto.HotelFindResponse;
import com.alivit.hotelservice.dto.ParamsDto;
import com.alivit.hotelservice.handler.exception.ResourceNotCreatedException;
import com.alivit.hotelservice.handler.exception.ResourceNotFoundException;
import com.alivit.hotelservice.mapper.HotelMapper;
import com.alivit.hotelservice.model.Hotel;
import com.alivit.hotelservice.repository.HotelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelServiceImplTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private HotelMapper hotelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private static HotelCreateRequest request;
    private static Hotel hotel;

    @BeforeAll
    static void init() {
        request = HotelTestData.getHotelCreateRequest();
        hotel = HotelTestData.getHotel();
    }

    @Nested
    class CreateHotel{

        @Test
        public void createShouldReturnSuccessfulBody(){
            HotelCreateResponse expectedResponse = HotelTestData.getHotelCreateResponse();

            when(hotelMapper.hotelCreateRequestToHotel(any())).thenReturn(hotel);
            when(hotelRepository.save(any())).thenReturn(hotel);
            when(hotelMapper.hotelToHotelCreateResponse(any())).thenReturn(expectedResponse);
            HotelCreateResponse actualResponse = hotelService.save(request);

            assertNotNull(actualResponse);
            assertEquals(expectedResponse, actualResponse);

            verify(hotelMapper, times(1)).hotelCreateRequestToHotel(any());
            verify(hotelRepository, times(1)).save(any());
            verify(hotelMapper, times(1)).hotelToHotelCreateResponse(any());
        }

        @Test
        public void createShouldReturnException(){
            when(hotelMapper.hotelCreateRequestToHotel(any())).thenReturn(hotel);
            when(hotelRepository.save(any()))
                    .thenThrow(new DataIntegrityViolationException(""));

            assertThrows(ResourceNotCreatedException.class, () -> hotelService.save(request));

            verify(hotelMapper, times(1)).hotelCreateRequestToHotel(any());
            verify(hotelRepository, times(1)).save(any());
            verify(hotelMapper, never()).hotelToHotelCreateResponse(any());
        }
    }

    @Nested
    class FindById {

        @Test
        public void findByIdShouldReturnSuccessfulBody() {
            HotelFindResponse expectedResponse = HotelTestData.getHotelFindResponse();

            when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.of(hotel));
            when(hotelMapper.hotelToHotelFindResponse(any())).thenReturn(expectedResponse);

            HotelFindResponse actualResponse = hotelService.findById(hotel.getId());

            assertNotNull(actualResponse);
            assertEquals(expectedResponse, actualResponse);

            verify(hotelRepository, times(1)).findById(any());
            verify(hotelMapper, times(1)).hotelToHotelFindResponse(any());
        }

        @Test
        public void findByIdShouldReturnException() {
            when(hotelRepository.findById(hotel.getId())).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> hotelService.findById(hotel.getId()));
            verify(hotelMapper, never()).hotelToHotelFindResponse(any());
        }
    }

    @Nested
    class FindAll {

        private Pageable pageable;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 10);
        }

        @Test
        void findAllShouldReturnFirstPage() {
            HotelCreateResponse expectedResponse = HotelTestData.getHotelCreateResponse();
            List<Hotel> hotels = List.of(
                    HotelTestData.getHotel(),
                    HotelTestData.getHotel()
            );
            Page<Hotel> hotelPage = new PageImpl<>(hotels, pageable, 100);

            when(hotelRepository.findAll(pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(any())).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findAll(pageable);

            assertNotNull(result);
            assertEquals(2, result.getContent().size());
            assertEquals(100, result.getTotalElements());
            assertEquals(10, result.getTotalPages());
            assertEquals(0, result.getNumber());

            verify(hotelRepository, times(1)).findAll(pageable);
            verify(hotelMapper, times(2)).hotelToHotelCreateResponse(any());
        }

        @Test
        void findAllShouldReturnEmptyPage() {
            Page<Hotel> hotelPage = Page.empty(pageable);
            when(hotelRepository.findAll(pageable)).thenReturn(hotelPage);

            Page<HotelCreateResponse> result = hotelService.findAll(pageable);

            assertNotNull(result);
            assertTrue(result.isEmpty());
            assertEquals(0, result.getTotalElements());
            assertEquals(0, result.getContent().size());

            verify(hotelRepository, times(1)).findAll(pageable);
            verify(hotelMapper, never()).hotelToHotelCreateResponse(any());
        }
    }

    @Nested
    class FindByParams {

        private Pageable pageable;
        private HotelCreateResponse expectedResponse;

        @BeforeEach
        void setUp() {
            pageable = PageRequest.of(0, 10);
            expectedResponse = HotelTestData.getHotelCreateResponse();
        }

        @Test
        void findByParamsShouldFilterByName() {
            ParamsDto paramsDto = ParamsDto.builder().name(hotel.getName()).build();
            Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), pageable, 1);

            when(hotelRepository.findByParams(paramsDto, pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(hotel)).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findByParams(pageable, paramsDto);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            assertEquals(hotel.getName(), result.getContent().getFirst().name());

            verify(hotelRepository, times(1)).findByParams(paramsDto, pageable);
            verify(hotelMapper, times(1)).hotelToHotelCreateResponse(hotel);
        }

        @Test
        void findByParamsShouldFilterByBrand() {
            ParamsDto paramsDto = ParamsDto.builder().name(hotel.getBrand()).build();
            Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), pageable, 1);

            when(hotelRepository.findByParams(paramsDto, pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(hotel)).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findByParams(pageable, paramsDto);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            verify(hotelRepository, times(1)).findByParams(paramsDto, pageable);
        }

        @Test
        void findByParamsShouldFilterByCity() {
            ParamsDto paramsDto = ParamsDto.builder().city(hotel.getAddress().getCity()).build();
            Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel, hotel, hotel), pageable, 3);

            when(hotelRepository.findByParams(paramsDto, pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(any())).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findByParams(pageable, paramsDto);

            assertNotNull(result);
            assertEquals(3, result.getTotalElements());
            verify(hotelRepository, times(1))
                    .findByParams(argThat(p -> hotel.getAddress().getCity().equals(p.city())), eq(pageable));
        }

        @Test
        void findByParamsShouldFilterByCountry() {
            ParamsDto paramsDto = ParamsDto.builder().name(hotel.getAddress().getCountry()).build();
            Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel), pageable, 5);

            when(hotelRepository.findByParams(paramsDto, pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(any())).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findByParams(pageable, paramsDto);

            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            verify(hotelRepository, times(1)).findByParams(paramsDto, pageable);
        }

        @Test
        void findByParamsShouldFilterBySingleAmenity() {
            ParamsDto paramsDto = ParamsDto.builder().amenities("Free parking").build();
            Page<Hotel> hotelPage = new PageImpl<>(List.of(hotel, hotel), pageable, 2);

            when(hotelRepository.findByParams(paramsDto, pageable)).thenReturn(hotelPage);
            when(hotelMapper.hotelToHotelCreateResponse(any())).thenReturn(expectedResponse);

            Page<HotelCreateResponse> result = hotelService.findByParams(pageable, paramsDto);

            assertNotNull(result);
            assertEquals(2, result.getTotalElements());
            verify(hotelRepository, times(1))
                    .findByParams(argThat(p -> p.amenities().contains("Free parking")), eq(pageable));
        }
    }
}