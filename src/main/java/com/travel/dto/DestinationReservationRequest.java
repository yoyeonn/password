package com.travel.dto;

import java.time.LocalDate;

public class DestinationReservationRequest {
  private Long destinationId;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private int adults;
  private int children;
  private double totalAmount;

  public Long getDestinationId() { return destinationId; }
  public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

  public int getAdults() { return adults; }
  public void setAdults(int adults) { this.adults = adults; }

  public int getChildren() { return children; }
  public void setChildren(int children) { this.children = children; }

  public double getTotalAmount() { return totalAmount; }
  public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
