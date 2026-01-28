package com.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.dto.PackReservationHistoryDTO;
import com.travel.entity.PackReservation;
import com.travel.repository.PackReservationRepository;

@Service
public class PackReservationService {

  private final PackReservationRepository repo;

  public PackReservationService(PackReservationRepository repo) {
    this.repo = repo;
  }

  public PackReservation create(PackReservation r) {
    return repo.save(r);
  }

  public PackReservation getById(Long id) {
    return repo.findById(id).orElseThrow();
  }

  public List<PackReservation> findByUser(Long userId) {
    return repo.findByUserIdOrderByCreatedAtDesc(userId);
  }

  public List<PackReservationHistoryDTO> myHistory(Long userId) {
    return findByUser(userId)
        .stream()
        .map(PackReservationHistoryDTO::from)
        .toList();
  }
}
