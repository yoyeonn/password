package com.travel.repository;

import com.travel.entity.DestinationActivity;
import com.travel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationActivityRepository extends JpaRepository<DestinationActivity, Long> {
    List<DestinationActivity> findByDestination(Destination destination);
}
