package com.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.dto.DestinationReservationHistoryDTO;
import com.travel.entity.DestinationReservation;
import com.travel.repository.DestinationReservationRepository;

@Service
public class DestinationReservationService {

  private final DestinationReservationRepository repo;

  public DestinationReservationService(DestinationReservationRepository repo) {
    this.repo = repo;
  }

  public DestinationReservation create(DestinationReservation r) {
    return repo.save(r);
  }

  public List<DestinationReservation> findByUser(Long userId) {
    return repo.findByUserIdOrderByCreatedAtDesc(userId);
  }

  public DestinationReservation getById(Long id) {
    return repo.findById(id).orElseThrow();
  }

  public List<DestinationReservationHistoryDTO> myHistory(Long userId) {
    return findByUser(userId).stream().map(DestinationReservationHistoryDTO::from).toList();
  }
}
