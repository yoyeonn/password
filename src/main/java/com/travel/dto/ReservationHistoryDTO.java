package com.travel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.travel.entity.HotelReservation;

public class ReservationHistoryDTO {
    public Long id;
    public LocalDateTime createdAt;
    public String hotelName;
    public LocalDate checkIn;
    public LocalDate checkOut;
    public double amount;
    public String mealPlan;
    public String roomNames;

    public static ReservationHistoryDTO from(HotelReservation r) {
        ReservationHistoryDTO dto = new ReservationHistoryDTO();
        dto.id = r.getId();
        dto.createdAt = r.getCreatedAt();
        dto.hotelName = r.getHotel().getName();
        dto.checkIn = r.getCheckIn();
        dto.checkOut = r.getCheckOut();
        dto.amount = r.getTotalAmount();
        dto.mealPlan = r.getMealPlan();
        dto.roomNames = r.getRoomNames();
        return dto;
    }
}
