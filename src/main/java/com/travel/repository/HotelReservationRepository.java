package com.travel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.entity.HotelReservation;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {

    @Query("""
      SELECT COUNT(r) FROM HotelReservation r
      WHERE r.hotel.id = :hotelId
      AND r.checkIn < :checkOut
      AND r.checkOut > :checkIn
    """)
    long countOverlappingReservations(
        @Param("hotelId") Long hotelId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );

    List<HotelReservation> findByUser_IdOrderByCreatedAtDesc(Long userId);

    List<HotelReservation> findAllByOrderByCreatedAtDesc();
}
