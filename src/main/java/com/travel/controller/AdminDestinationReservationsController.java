package com.travel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.repository.DestinationReservationRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminDestinationReservationsController {

  private final DestinationReservationRepository repo;

  public AdminDestinationReservationsController(DestinationReservationRepository repo) {
    this.repo = repo;
  }

  @GetMapping("/destinations")
  public ResponseEntity<?> getAllDestinations() {
    var list = repo.findAllByOrderByCreatedAtDesc()
      .stream()
      .map(r -> {
        var dto = new java.util.HashMap<String, Object>();
        dto.put("id", r.getId());
        dto.put("createdAt", r.getCreatedAt());

        dto.put("destinationId", r.getDestination().getId());
        dto.put("destinationName", r.getDestination().getName());
        dto.put("destinationCountry", r.getDestination().getCountry());
        dto.put("destinationLocation", r.getDestination().getLocation());

        dto.put("userId", r.getUser().getId());
        dto.put("userName", r.getUser().getName());
        dto.put("userEmail", r.getUser().getEmail());

        dto.put("checkIn", r.getCheckIn());
        dto.put("checkOut", r.getCheckOut());
        dto.put("adults", r.getAdults());
        dto.put("children", r.getChildren());
        dto.put("totalAmount", r.getTotalAmount());
        return dto;
      })
      .toList();

    Map<String, Object> res = new HashMap<>();
    res.put("ok", true);
    res.put("status", HttpStatus.OK.value());
    res.put("message", "Destination reservations retrieved");
    res.put("data", list);

    return ResponseEntity.ok(res);
  }

  @GetMapping("/destinations/{id}")
  public ResponseEntity<?> getDestinationReservation(@PathVariable Long id) {
    var r = repo.findById(id).orElseThrow();

    var dto = new HashMap<String, Object>();
    dto.put("id", r.getId());
    dto.put("createdAt", r.getCreatedAt());

    dto.put("destinationId", r.getDestination().getId());
    dto.put("destinationName", r.getDestination().getName());
    dto.put("destinationCountry", r.getDestination().getCountry());
    dto.put("destinationLocation", r.getDestination().getLocation());

    dto.put("userId", r.getUser().getId());
    dto.put("userName", r.getUser().getName());
    dto.put("userEmail", r.getUser().getEmail());

    dto.put("checkIn", r.getCheckIn());
    dto.put("checkOut", r.getCheckOut());
    dto.put("adults", r.getAdults());
    dto.put("children", r.getChildren());
    dto.put("totalAmount", r.getTotalAmount());

    Map<String, Object> res = new HashMap<>();
    res.put("ok", true);
    res.put("status", HttpStatus.OK.value());
    res.put("message", "Destination reservation retrieved");
    res.put("data", dto);

    return ResponseEntity.ok(res);
  }
}
