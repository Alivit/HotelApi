package com.alivit.hotelservice.repository;

import com.alivit.hotelservice.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
