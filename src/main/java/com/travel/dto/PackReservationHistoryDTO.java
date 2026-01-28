package com.travel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travel.entity.PackReservation;

public class PackReservationHistoryDTO {
  public Long id;
  public LocalDateTime createdAt;

  public String packName;
  public double pricePerPerson;
  public String location;

  public String hotelName;
  public String destinationName;

  public LocalDate checkIn;
  public LocalDate checkOut;

  public int adults;
  public int children;

  // ✅ FORMULE
  public String mealPlan;
  public double mealPlanExtra;

  public double amount;

  public static PackReservationHistoryDTO from(PackReservation r) {
    PackReservationHistoryDTO dto = new PackReservationHistoryDTO();

    dto.id = r.getId();
    dto.createdAt = r.getCreatedAt();

    dto.packName = r.getPack().getName();
    dto.pricePerPerson = r.getPack().getPrice();

    // ✅ location always from destination when exists
    dto.location =
        (r.getPack().getDestination() != null)
            ? r.getPack().getDestination().getLocation()
            : null;

    dto.hotelName =
        (r.getPack().getHotel() != null)
            ? r.getPack().getHotel().getName()
            : null;

    dto.destinationName =
        (r.getPack().getDestination() != null)
            ? r.getPack().getDestination().getName()
            : null;

    dto.checkIn = r.getCheckIn();
    dto.checkOut = r.getCheckOut();

    dto.adults = r.getAdults();
    dto.children = r.getChildren();

    // ✅ formule
    dto.mealPlan = r.getMealPlan();
    dto.mealPlanExtra = r.getMealPlanExtra();

    dto.amount = r.getTotalAmount();
    return dto;
  }
}
