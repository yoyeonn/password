package com.travel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DestinationReservationResponse {
  private Long id;
  private Long destinationId;
  private String destinationName;

  private LocalDate checkIn;
  private LocalDate checkOut;

  private int adults;
  private int children;

  private double totalAmount;
  private LocalDateTime createdAt;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getDestinationId() { return destinationId; }
  public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

  public String getDestinationName() { return destinationName; }
  public void setDestinationName(String destinationName) { this.destinationName = destinationName; }

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

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
