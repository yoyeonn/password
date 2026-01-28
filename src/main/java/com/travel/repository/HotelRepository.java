package com.travel.repository;

import com.travel.entity.Hotel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE LOWER(CONCAT(h.name, ', ', h.city, ', ', h.country)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Hotel> searchHotels(@Param("keyword") String keyword);


    @Query("SELECT h FROM Hotel h WHERE " +
       "LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(h.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
       "LOWER(h.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Hotel> findSuggestions(@Param("keyword") String keyword);

    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.rooms WHERE h.id = :id")
Optional<Hotel> findByIdWithRooms(@Param("id") Long id);
}
