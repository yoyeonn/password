package com.travel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travel.dto.HotelReservationDTO;
import com.travel.entity.HotelReservation;
import com.travel.service.HotelReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationsController {

    private final HotelReservationService hotelReservationService;

    public AdminReservationsController(HotelReservationService hotelReservationService) {
        this.hotelReservationService = hotelReservationService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<Map<String, Object>> getAllHotelReservations() {
        Map<String, Object> res = new HashMap<>();
        try {
            List<HotelReservationDTO> list = hotelReservationService.getAllAdmin();
            res.put("ok", true);
            res.put("status", HttpStatus.OK.value());
            res.put("message", "Hotel reservations retrieved");
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            res.put("ok", false);
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", ex.getMessage());
            res.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Map<String, Object>> getHotelReservation(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            HotelReservationDTO dto = hotelReservationService.getByIdAdmin(id);
            res.put("ok", true);
            res.put("status", HttpStatus.OK.value());
            res.put("message", "Hotel reservation retrieved");
            res.put("data", dto);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            res.put("ok", false);
            res.put("status", HttpStatus.NOT_FOUND.value());
            res.put("message", ex.getMessage());
            res.put("error", "Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }

    // ✅ ADMIN invoice JSON (NO owner check)
    @GetMapping("/hotels/{id}/invoice")
    public ResponseEntity<Map<String, Object>> getInvoiceJsonAdmin(@PathVariable Long id) {
        HotelReservation r = hotelReservationService.getById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("id", r.getId());

        response.put("hotelId", r.getHotel().getId());
        response.put("hotelName", r.getHotel().getName());

        response.put("checkIn", r.getCheckIn());
        response.put("checkOut", r.getCheckOut());
        response.put("createdAt", r.getCreatedAt());

        response.put("adults", r.getAdults());
        response.put("children", r.getChildren());
        response.put("babies", r.getBabies());

        response.put("mealPlan", r.getMealPlan());
        response.put("totalAmount", r.getTotalAmount());

        response.put("roomIds", r.getRoomIds());
        response.put("roomNames", r.getRoomNames());
        response.put("roomPrices", r.getRoomPrices());

        response.put("roomAdults", r.getRoomAdults());
        response.put("roomChildren", r.getRoomChildren());
        response.put("roomBabies", r.getRoomBabies());

        // user info (nice for facture)
        response.put("userName", r.getUser().getName());
        response.put("userEmail", r.getUser().getEmail());

        return ResponseEntity.ok(response);
    }

    // ✅ ADMIN invoice PDF (server generated)
    @GetMapping("/hotels/{id}/invoice.pdf")
    public ResponseEntity<byte[]> downloadInvoicePdfAdmin(@PathVariable Long id) {
        HotelReservation r = hotelReservationService.getById(id);

        byte[] pdf = hotelReservationService.generateInvoicePdf(r);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=FAC-" + r.getId() + ".pdf")
                .body(pdf);
    }
}
