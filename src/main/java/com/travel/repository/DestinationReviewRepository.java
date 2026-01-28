package com.travel.repository;

import com.travel.entity.DestinationReview;
import com.travel.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationReviewRepository extends JpaRepository<DestinationReview, Long> {
    List<DestinationReview> findByDestination(Destination destination);
}
