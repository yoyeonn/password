package com.travel.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.travel.dto.DestinationReservationHistoryDTO;
import com.travel.dto.DestinationReservationRequest;
import com.travel.dto.DestinationReservationResponse;
import com.travel.entity.DestinationReservation;
import com.travel.repository.DestinationRepository;
import com.travel.repository.UserRepository;
import com.travel.service.DestinationReservationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/destination-reservations")
public class DestinationReservationController {

  private final DestinationReservationService service;
  private final DestinationRepository destinationRepo;
  private final UserRepository userRepo;

  public DestinationReservationController(
      DestinationReservationService service,
      DestinationRepository destinationRepo,
      UserRepository userRepo
  ) {
    this.service = service;
    this.destinationRepo = destinationRepo;
    this.userRepo = userRepo;
  }

  @PostMapping
  public ResponseEntity<?> reserve(@RequestBody DestinationReservationRequest req, Principal principal) {
    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    var destination = destinationRepo.findById(req.getDestinationId()).orElseThrow();

    DestinationReservation r = new DestinationReservation();
    r.setUser(user);
    r.setDestination(destination);
    r.setCheckIn(req.getCheckIn());
    r.setCheckOut(req.getCheckOut());
    r.setAdults(req.getAdults());
    r.setChildren(req.getChildren());
    r.setTotalAmount(req.getTotalAmount());

    DestinationReservation saved = service.create(r);

    DestinationReservationResponse resp = new DestinationReservationResponse();
    resp.setId(saved.getId());
    resp.setDestinationId(destination.getId());
    resp.setDestinationName(destination.getName());
    resp.setCheckIn(saved.getCheckIn());
    resp.setCheckOut(saved.getCheckOut());
    resp.setAdults(saved.getAdults());
    resp.setChildren(saved.getChildren());
    resp.setTotalAmount(saved.getTotalAmount());
    resp.setCreatedAt(saved.getCreatedAt());

    return ResponseEntity.ok(resp);
  }

  // ✅ PROFILE HISTORY
  @GetMapping("/me")
  public List<DestinationReservationHistoryDTO> myReservations(Principal principal) {
    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    return service.myHistory(user.getId());
  }

  // ✅ INVOICE JSON (for jsPDF on frontend)
  @GetMapping("/{id}/invoice")
  public ResponseEntity<Map<String, Object>> invoice(@PathVariable Long id, Principal principal) {
    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    DestinationReservation r = service.getById(id);

    if (!r.getUser().getId().equals(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your reservation");
    }

    Map<String, Object> inv = new HashMap<>();
    inv.put("id", r.getId());
    inv.put("createdAt", r.getCreatedAt());

    inv.put("checkIn", r.getCheckIn());
    inv.put("checkOut", r.getCheckOut());
    inv.put("adults", r.getAdults());
    inv.put("children", r.getChildren());
    inv.put("totalAmount", r.getTotalAmount());

    // destination info
    inv.put("destinationId", r.getDestination().getId());
    inv.put("destinationName", r.getDestination().getName());
    inv.put("country", r.getDestination().getCountry());
    inv.put("location", r.getDestination().getLocation());
    inv.put("pricePerPerson", r.getDestination().getPrice());

    // user info
    inv.put("userName", r.getUser().getName());
    inv.put("userEmail", r.getUser().getEmail());

    return ResponseEntity.ok(inv);
  }
}
