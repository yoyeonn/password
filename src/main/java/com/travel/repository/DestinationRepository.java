package com.travel.repository;

import com.travel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    @Query("""
        SELECT d FROM Destination d
        WHERE
            (:q IS NULL OR :q = '' OR
                LOWER(d.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(d.country) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(d.location) LIKE LOWER(CONCAT('%', :q, '%'))
            )
        AND (:maxPrice IS NULL OR d.price <= :maxPrice)
        AND (
            :checkIn IS NULL OR :checkOut IS NULL OR
            (d.availableFrom <= :checkIn AND d.availableTo >= :checkOut)
        )
    """)
    List<Destination> search(
        @Param("q") String q,
        @Param("maxPrice") Double maxPrice,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );
}
