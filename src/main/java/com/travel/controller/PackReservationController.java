package com.travel.controller;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.PackReservationRequestDTO;
import com.travel.entity.PackReservation;
import com.travel.repository.PacksRepository;
import com.travel.repository.UserRepository;
import com.travel.service.PackReservationService;

@RestController
@RequestMapping("/api/pack-reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class PackReservationController {

  private final PackReservationService service;
  private final PacksRepository packsRepo;
  private final UserRepository userRepo;

  public PackReservationController(
      PackReservationService service,
      PacksRepository packsRepo,
      UserRepository userRepo
  ) {
    this.service = service;
    this.packsRepo = packsRepo;
    this.userRepo = userRepo;
  }

  private com.travel.entity.User authUser() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepo.findByEmail(email).orElseThrow();
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody PackReservationRequestDTO payload) {
    var u = authUser();

    var pack = packsRepo.findById(payload.packId)
      .orElseThrow(() -> new RuntimeException("Pack not found"));

    PackReservation r = new PackReservation();
    r.setUser(u);
    r.setPack(pack);

    r.setCheckIn(payload.checkIn);
    r.setCheckOut(payload.checkOut);

    // computed from rooms in frontend
    r.setAdults(payload.adults);
    r.setChildren(payload.children);

    // formule
    r.setMealPlan(payload.mealPlan);
    r.setMealPlanExtra(payload.mealPlanExtra);

    r.setTotalAmount(payload.totalAmount);

    r.setRoomIds(payload.roomIds);
    r.setRoomNames(payload.roomNames);
    r.setRoomPrices(payload.roomPrices);
    r.setRoomAdults(payload.roomAdults);
    r.setRoomChildren(payload.roomChildren);
    r.setRoomBabies(payload.roomBabies);

    var saved = service.create(r);

    Map<String, Object> res = new HashMap<>();
    res.put("data", saved.getId());
    return ResponseEntity.ok(res);
  }

  @GetMapping("/me")
  public ResponseEntity<?> myHistory() {
    var u = authUser();
    return ResponseEntity.ok(Map.of("data", service.myHistory(u.getId())));
  }

  @GetMapping("/{id}/invoice")
  public ResponseEntity<?> invoice(@PathVariable Long id) {
    var u = authUser();
    var r = service.getById(id);

    if (!r.getUser().getId().equals(u.getId())) {
      return ResponseEntity.status(403).body(Map.of("message", "Forbidden"));
    }

    return ResponseEntity.ok(invoiceJson(r));
  }

  private Map<String, Object> invoiceJson(PackReservation r) {
    Map<String, Object> inv = new HashMap<>();
    inv.put("id", r.getId());
    inv.put("createdAt", r.getCreatedAt());

    inv.put("checkIn", r.getCheckIn());
    inv.put("checkOut", r.getCheckOut());

    inv.put("adults", r.getAdults());
    inv.put("children", r.getChildren());

    inv.put("mealPlan", r.getMealPlan());
    inv.put("mealPlanExtra", r.getMealPlanExtra());

    inv.put("totalAmount", r.getTotalAmount());

    inv.put("userName", r.getUser().getName());
    inv.put("userEmail", r.getUser().getEmail());

    inv.put("packId", r.getPack().getId());
    inv.put("packName", r.getPack().getName());
    inv.put("pricePerPerson", r.getPack().getPrice());

    inv.put("location",
        r.getPack().getDestination() != null ? r.getPack().getDestination().getLocation() : null);

    inv.put("hotelName",
        r.getPack().getHotel() != null ? r.getPack().getHotel().getName() : null);

    inv.put("destinationName",
        r.getPack().getDestination() != null ? r.getPack().getDestination().getName() : null);

    // ✅ IMPORTANT: computed totals for facture
    long nights = 0;
    if (r.getCheckIn() != null && r.getCheckOut() != null) {
      nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
      if (nights < 0) nights = 0;
    }

    int payingPeople = (r.getAdults() + r.getChildren());
    double basePackTotal = r.getPack().getPrice() * payingPeople * nights;
    double mealPlanTotal = r.getMealPlanExtra() * payingPeople * nights;

    inv.put("nights", nights);
    inv.put("payingPeople", payingPeople);
    inv.put("basePackTotal", basePackTotal);
    inv.put("mealPlanTotal", mealPlanTotal);

    inv.put("roomIds", r.getRoomIds());
    inv.put("roomNames", r.getRoomNames());
    inv.put("roomPrices", r.getRoomPrices());
    inv.put("roomAdults", r.getRoomAdults());
    inv.put("roomChildren", r.getRoomChildren());
    inv.put("roomBabies", r.getRoomBabies());

    return inv;
  }
}
