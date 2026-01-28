package com.travel.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotel_reservations")
public class HotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate checkIn;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getBabies() {
        return babies;
    }

    public void setBabies(int babies) {
        this.babies = babies;
    }

    public String getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(String mealPlan) {
        this.mealPlan = mealPlan;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private LocalDate checkOut;

    private int adults;
    private int children;
    private int babies;

    private String mealPlan;

    private double totalAmount;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String roomNames;

    public String getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(String roomNames) {
        this.roomNames = roomNames;
    }

    public String getRoomPrices() {
        return roomPrices;
    }

    public void setRoomPrices(String roomPrices) {
        this.roomPrices = roomPrices;
    }

    @Column(columnDefinition = "TEXT")
    private String roomPrices;

    @Column(columnDefinition = "TEXT")
    private String roomIds;
    public String getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(String roomIds) {
        this.roomIds = roomIds;
    }

    @Column(columnDefinition = "TEXT")
    private String roomAdults;

    public String getRoomAdults() {
        return roomAdults;
    }

    public void setRoomAdults(String roomAdults) {
        this.roomAdults = roomAdults;
    }

    public String getRoomChildren() {
        return roomChildren;
    }

    public void setRoomChildren(String roomChildren) {
        this.roomChildren = roomChildren;
    }

    public String getRoomBabies() {
        return roomBabies;
    }

    public void setRoomBabies(String roomBabies) {
        this.roomBabies = roomBabies;
    }

    @Column(columnDefinition = "TEXT")
    private String roomChildren;

    @Column(columnDefinition = "TEXT")
    private String roomBabies;

}


