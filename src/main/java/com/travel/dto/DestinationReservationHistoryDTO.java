package com.travel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travel.entity.DestinationReservation;

public class DestinationReservationHistoryDTO {
  public Long id;
  public LocalDateTime createdAt;
  public String destinationName;
  public String country;
  public String location;
  public LocalDate checkIn;
  public LocalDate checkOut;
  public int adults;
  public int children;
  public double amount;

  public static DestinationReservationHistoryDTO from(DestinationReservation r) {
    DestinationReservationHistoryDTO dto = new DestinationReservationHistoryDTO();
    dto.id = r.getId();
    dto.createdAt = r.getCreatedAt();
    dto.destinationName = r.getDestination().getName();
    dto.country = r.getDestination().getCountry();
    dto.location = r.getDestination().getLocation();
    dto.checkIn = r.getCheckIn();
    dto.checkOut = r.getCheckOut();
    dto.adults = r.getAdults();
    dto.children = r.getChildren();
    dto.amount = r.getTotalAmount();
    return dto;
  }
}
