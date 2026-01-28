package com.travel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "pack_reservations")
public class PackReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "pack_id")
  private Packs pack;

  private LocalDate checkIn;
  private LocalDate checkOut;

  private int adults;
  private int children;

  // ✅ FORMULE
  @Column(length = 30)
  private String mealPlan;

  private double mealPlanExtra;

  private double totalAmount;

  // Store room info as CSV
  @Column(length = 2000)
  private String roomIds;

  @Column(length = 2000)
  private String roomNames;

  @Column(length = 2000)
  private String roomPrices;

  @Column(length = 2000)
  private String roomAdults;

  @Column(length = 2000)
  private String roomChildren;

  @Column(length = 2000)
  private String roomBabies;

  @CreationTimestamp
  private LocalDateTime createdAt;

  // getters/setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }

  public Packs getPack() { return pack; }
  public void setPack(Packs pack) { this.pack = pack; }

  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

  public int getAdults() { return adults; }
  public void setAdults(int adults) { this.adults = adults; }

  public int getChildren() { return children; }
  public void setChildren(int children) { this.children = children; }

  public String getMealPlan() { return mealPlan; }
  public void setMealPlan(String mealPlan) { this.mealPlan = mealPlan; }

  public double getMealPlanExtra() { return mealPlanExtra; }
  public void setMealPlanExtra(double mealPlanExtra) { this.mealPlanExtra = mealPlanExtra; }

  public double getTotalAmount() { return totalAmount; }
  public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

  public String getRoomIds() { return roomIds; }
  public void setRoomIds(String roomIds) { this.roomIds = roomIds; }

  public String getRoomNames() { return roomNames; }
  public void setRoomNames(String roomNames) { this.roomNames = roomNames; }

  public String getRoomPrices() { return roomPrices; }
  public void setRoomPrices(String roomPrices) { this.roomPrices = roomPrices; }

  public String getRoomAdults() { return roomAdults; }
  public void setRoomAdults(String roomAdults) { this.roomAdults = roomAdults; }

  public String getRoomChildren() { return roomChildren; }
  public void setRoomChildren(String roomChildren) { this.roomChildren = roomChildren; }

  public String getRoomBabies() { return roomBabies; }
  public void setRoomBabies(String roomBabies) { this.roomBabies = roomBabies; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
