package com.travel.repository;

import com.travel.entity.DestinationReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinationReservationRepository extends JpaRepository<DestinationReservation, Long> {
  List<DestinationReservation> findByUserIdOrderByCreatedAtDesc(Long userId);
  List<DestinationReservation> findAllByOrderByCreatedAtDesc();
}
