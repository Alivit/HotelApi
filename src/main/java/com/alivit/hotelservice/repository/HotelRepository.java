package com.alivit.hotelservice.repository;

import com.alivit.hotelservice.dto.ParamsDto;
import com.alivit.hotelservice.model.Address;
import com.alivit.hotelservice.model.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query(value = "SELECT BRAND, COUNT(*) FROM HOTELS GROUP BY BRAND", nativeQuery = true)
    List<Object[]> groupByBrand();

    @Query(value = "SELECT A.CITY, COUNT(*) FROM HOTELS "
            + "LEFT JOIN PUBLIC.ADDRESS AS A on A.ID = HOTELS.ADDRESS_ID "
            + "GROUP BY A.CITY", nativeQuery = true)
    List<Object[]> groupByCity();

    @Query(value = "SELECT A.AMENITY, COUNT(*) FROM HOTELS "
            + "LEFT JOIN PUBLIC.AMENITIES AS A on A.HOTEL_ID = HOTELS.ID "
            + "GROUP BY A.AMENITY", nativeQuery = true)
    List<Object[]> groupByAmenity();

    @Query(value = "SELECT A.COUNTRY, COUNT(*) FROM HOTELS "
            + "LEFT JOIN PUBLIC.ADDRESS AS A on A.ID = HOTELS.ADDRESS_ID "
            + "GROUP BY A.COUNTRY", nativeQuery = true)
    List<Object[]> groupByCountry();

    default Page<Hotel> findByParams(ParamsDto params, Pageable pageable) {
        if (params == null) {
            return findAll(pageable);
        }

        Specification<Hotel> specification = Specification.where(nameLike(params.name()))
                .and(brandLike(params.brand()))
                .and(cityLike(params.city()))
                .and(countryLike(params.country()))
                .and(amenityLike(params.amenities()));
        return findAll(specification, pageable);
    }

    default Specification<Hotel> nameLike(String name) {
        return (hotel, cq, cb) -> isNotEmpty(name)
                ? cb.like(cb.lower(hotel.get("name")), "%" + name.toLowerCase() + "%")
                : cb.conjunction();
    }

    default Specification<Hotel> brandLike(String brand) {
        return (hotel, cq, cb) -> isNotEmpty(brand)
                ? cb.like(cb.lower(hotel.get("brand")), "%" + brand.toLowerCase() + "%")
                : cb.conjunction();
    }

    default Specification<Hotel> countryLike(String country) {
        return (hotel, cq, cb) -> isNotEmpty(country)
                ? cb.like(cb.lower(hotel.get("country")), "%" + country.toLowerCase() + "%")
                : cb.conjunction();
    }

    default Specification<Hotel> cityLike(String city) {
        return (hotel, cq, cb) -> {
            Join<Hotel, Address> address = hotel.join("address", JoinType.LEFT);
            return isNotEmpty(city)
                    ? cb.like(cb.lower(address.get("city")), "%" + city.toLowerCase() + "%")
                    : cb.conjunction();
        };
    }

    default Specification<Hotel> amenityLike(String amenity) {
        return (hotel, cq, cb) -> {
            Join<Hotel, String> amenities = hotel.join("amenities", JoinType.LEFT);
            return isNotEmpty(amenity)
                    ? cb.like(cb.lower(amenities), "%" + amenity.toLowerCase() + "%")
                    : cb.conjunction();
        };
    }

    private static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
