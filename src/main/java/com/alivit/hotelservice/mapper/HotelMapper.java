package com.alivit.hotelservice.mapper;

import com.alivit.hotelservice.dto.HotelCreateRequest;
import com.alivit.hotelservice.dto.HotelCreateResponse;
import com.alivit.hotelservice.model.Address;
import com.alivit.hotelservice.model.Hotel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface HotelMapper {

    Hotel hotelCreateRequestToHotel(HotelCreateRequest hotelCreateRequest);

    @Mapping(source = "contact.phone", target = "phone")
    @Mapping(source = "address", target = "address", qualifiedByName = "addressToString")
    HotelCreateResponse hotelToHotelCreateResponse(Hotel hotel);

    @Named("addressToString")
    public static String addressToString(Address address){
        return address.getHouseNumber() + " " + address.getStreet()
                + ", " + address.getCity() + ", " + address.getPostCode()
                + ", " + address.getCountry();
    }
}
