package com.travel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "destination_reservations")
public class DestinationReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "destination_id", nullable = false)
  private Destination destination;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private LocalDate checkIn;
  private LocalDate checkOut;

  private int adults;
  private int children;

  private double totalAmount;

  private LocalDateTime createdAt = LocalDateTime.now();

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Destination getDestination() { return destination; }
  public void setDestination(Destination destination) { this.destination = destination; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

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
