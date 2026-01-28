package com.travel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.repository.PackReservationRepository;

@RestController
@RequestMapping("/api/admin/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminPackReservationsController {

  private final PackReservationRepository repo;

  public AdminPackReservationsController(PackReservationRepository repo) {
    this.repo = repo;
  }

  /**
   * ✅ Price rule:
   * pricePerPersonPerNight = destination.price + firstRoom.price
   * - destination.price from Destination entity
   * - firstRoom.price = hotel.rooms[0].price (if exists)
   */
  private double packPricePerPersonPerNight(com.travel.entity.PackReservation r) {
    double destinationPrice = 0;
    if (r.getPack() != null && r.getPack().getDestination() != null) {
      destinationPrice = r.getPack().getDestination().getPrice();
    }

    double firstRoomPrice = 0;
    if (r.getPack() != null
        && r.getPack().getHotel() != null
        && r.getPack().getHotel().getRooms() != null
        && !r.getPack().getHotel().getRooms().isEmpty()) {

      firstRoomPrice = r.getPack().getHotel().getRooms().get(0).getPrice();
    }

    return destinationPrice + firstRoomPrice;
  }

  @GetMapping("/packs")
  public ResponseEntity<?> getAll() {
    var list = repo.findAllByOrderByCreatedAtDesc().stream().map(r -> {
      Map<String, Object> dto = new HashMap<>();
      dto.put("id", r.getId());
      dto.put("createdAt", r.getCreatedAt());

      dto.put("packId", r.getPack().getId());
      dto.put("packName", r.getPack().getName());
      dto.put("packLocation",
          r.getPack().getDestination() != null ? r.getPack().getDestination().getLocation() : null);

      // ✅ price per person per night = destination + first room
      dto.put("pricePerPerson", packPricePerPersonPerNight(r));

      // ✅ FORMULE
      dto.put("mealPlan", r.getMealPlan());
      dto.put("mealPlanExtra", r.getMealPlanExtra());

      dto.put("userId", r.getUser().getId());
      dto.put("userName", r.getUser().getName());
      dto.put("userEmail", r.getUser().getEmail());

      dto.put("checkIn", r.getCheckIn());
      dto.put("checkOut", r.getCheckOut());

      dto.put("adults", r.getAdults());
      dto.put("children", r.getChildren());

      dto.put("totalAmount", r.getTotalAmount());
      return dto;
    }).toList();

    return ResponseEntity.ok(Map.of("data", list));
  }

  @GetMapping("/packs/{id}")
  public ResponseEntity<?> getOne(@PathVariable Long id) {
    var r = repo.findById(id).orElseThrow();

    Map<String, Object> dto = new HashMap<>();
    dto.put("id", r.getId());
    dto.put("createdAt", r.getCreatedAt());

    dto.put("packId", r.getPack().getId());
    dto.put("packName", r.getPack().getName());
    dto.put("packLocation",
        r.getPack().getDestination() != null ? r.getPack().getDestination().getLocation() : null);

    // ✅ correct price
    dto.put("pricePerPerson", packPricePerPersonPerNight(r));

    dto.put("hotelName", r.getPack().getHotel() != null ? r.getPack().getHotel().getName() : null);
    dto.put("destinationName", r.getPack().getDestination() != null ? r.getPack().getDestination().getName() : null);

    dto.put("userName", r.getUser().getName());
    dto.put("userEmail", r.getUser().getEmail());

    dto.put("checkIn", r.getCheckIn());
    dto.put("checkOut", r.getCheckOut());

    dto.put("adults", r.getAdults());
    dto.put("children", r.getChildren());

    // ✅ FORMULE
    dto.put("mealPlan", r.getMealPlan());
    dto.put("mealPlanExtra", r.getMealPlanExtra());

    dto.put("totalAmount", r.getTotalAmount());

    // rooms csv
    dto.put("roomNames", r.getRoomNames());
    dto.put("roomPrices", r.getRoomPrices());
    dto.put("roomAdults", r.getRoomAdults());
    dto.put("roomChildren", r.getRoomChildren());
    dto.put("roomBabies", r.getRoomBabies());

    return ResponseEntity.ok(Map.of("data", dto));
  }

  // ✅ Admin invoice JSON (no owner check)
  @GetMapping("/packs/{id}/invoice")
  public ResponseEntity<?> invoice(@PathVariable Long id) {
    var r = repo.findById(id).orElseThrow();

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

    // ✅ correct price
    double pricePerPerson = packPricePerPersonPerNight(r);
    inv.put("pricePerPerson", pricePerPerson);

    inv.put("location",
        r.getPack().getDestination() != null ? r.getPack().getDestination().getLocation() : null);

    inv.put("hotelName", r.getPack().getHotel() != null ? r.getPack().getHotel().getName() : null);
    inv.put("destinationName", r.getPack().getDestination() != null ? r.getPack().getDestination().getName() : null);

    long nights = 0;
    if (r.getCheckIn() != null && r.getCheckOut() != null) {
      nights = java.time.temporal.ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
      if (nights < 0) nights = 0;
    }

    int payingPeople = r.getAdults() + r.getChildren();

    // ✅ formulas use correct price
    double basePackTotal = pricePerPerson * payingPeople * nights;
    double mealPlanTotal = r.getMealPlanExtra() * payingPeople * nights;

    inv.put("nights", nights);
    inv.put("payingPeople", payingPeople);
    inv.put("basePackTotal", basePackTotal);
    inv.put("mealPlanTotal", mealPlanTotal);

    inv.put("roomNames", r.getRoomNames());
    inv.put("roomPrices", r.getRoomPrices());
    inv.put("roomAdults", r.getRoomAdults());
    inv.put("roomChildren", r.getRoomChildren());
    inv.put("roomBabies", r.getRoomBabies());

    return ResponseEntity.ok(inv);
  }
}
