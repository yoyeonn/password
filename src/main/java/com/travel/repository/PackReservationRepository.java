package com.travel.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.entity.PackReservation;

public interface PackReservationRepository extends JpaRepository<PackReservation, Long> {
  List<PackReservation> findByUserIdOrderByCreatedAtDesc(Long userId);
  List<PackReservation> findAllByOrderByCreatedAtDesc();
}
