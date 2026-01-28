package com.travel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class HotelReservationDTO {

    private Long id;

    private Long userId;
    private String userName;
    private String userEmail;

    private Long hotelId;
    private String hotelName;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private int adults;
    private int children;
    private int babies;

    private String mealPlan;
    private double totalAmount;

    private LocalDateTime createdAt;

    // ✅ THIS is what your service uses: dto.setRoomNames(...)
    private List<String> roomNames;

    // ===== getters / setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public int getBabies() { return babies; }
    public void setBabies(int babies) { this.babies = babies; }

    public String getMealPlan() { return mealPlan; }
    public void setMealPlan(String mealPlan) { this.mealPlan = mealPlan; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ✅ MISSING METHODS (your error)
    public List<String> getRoomNames() { return roomNames; }
    public void setRoomNames(List<String> roomNames) { this.roomNames = roomNames; }
}
