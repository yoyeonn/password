package com.travel.repository;

import com.travel.entity.DestinationFAQ;
import com.travel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationFAQRepository extends JpaRepository<DestinationFAQ, Long> {
    List<DestinationFAQ> findByDestination(Destination destination);
}
