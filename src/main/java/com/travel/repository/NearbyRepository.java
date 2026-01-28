package com.travel.repository;

import com.travel.entity.Nearby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NearbyRepository extends JpaRepository<Nearby, Long> {
    List<Nearby> findByHotelId(Long hotelId);
}
