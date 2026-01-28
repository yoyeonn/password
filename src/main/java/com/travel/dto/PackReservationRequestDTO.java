package com.travel.dto;

import java.time.LocalDate;

public class PackReservationRequestDTO {
  public Long packId;
  public LocalDate checkIn;
  public LocalDate checkOut;
  public int adults;
  public int children;

  // ✅ FORMULE
  public String mealPlan;      // ROOM_ONLY, BB, HB, FB, AI, UAI
  public double mealPlanExtra; // extra per person per night (optional but useful)

  public double totalAmount;

  public String roomIds;
  public String roomNames;
  public String roomPrices;
  public String roomAdults;
  public String roomChildren;
  public String roomBabies;
}
