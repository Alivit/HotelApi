package com.alivit.hotelservice.repository;
import com.alivit.hotelservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface AmenityRepository extends JpaRepository<Hotel, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT amenity FROM amenities WHERE hotel_id = ?1", nativeQuery = true)
    Set<String> getAmenitiesNative(Long hotelId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO amenities (hotel_id, amenity) VALUES (:hotelId, :amenity)",
            nativeQuery = true)
    void insertAmenity(Long hotelId, String amenity);

    boolean existsHotelById(Long id);
}
