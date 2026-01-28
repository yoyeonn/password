package com.travel.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.travel.dto.HotelReservationRequestDTO;
import com.travel.dto.ReservationHistoryDTO;
import com.travel.entity.HotelReservation;
import com.travel.repository.HotelRepository;
import com.travel.repository.UserRepository;
import com.travel.service.HotelReservationService;

@RestController
@RequestMapping("/api/reservations")
public class HotelReservationController {

    private final HotelReservationService hotelReservationService;
    private final HotelRepository hotelRepo;
    private final UserRepository userRepo;

    public HotelReservationController(
            HotelReservationService hotelReservationService,
            HotelRepository hotelRepo,
            UserRepository userRepo
    ) {
        this.hotelReservationService = hotelReservationService;
        this.hotelRepo = hotelRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> reserve(@RequestBody HotelReservationRequestDTO req, Principal principal) {

        var user = userRepo.findByEmail(principal.getName()).orElseThrow();
        var hotel = hotelRepo.findById(req.getHotelId()).orElseThrow();

        HotelReservation res = new HotelReservation();
        res.setHotel(hotel);
        res.setUser(user);
        res.setCheckIn(req.getCheckIn());
        res.setCheckOut(req.getCheckOut());
        res.setMealPlan(req.getMealPlan());
        res.setTotalAmount(req.getTotalAmount());

        int adults = 0;
        int children = 0;
        int babies = 0;

        StringBuilder roomIds = new StringBuilder();
        StringBuilder roomNames = new StringBuilder();
        StringBuilder roomPrices = new StringBuilder();

        StringBuilder roomAdults = new StringBuilder();
        StringBuilder roomChildren = new StringBuilder();
        StringBuilder roomBabies = new StringBuilder();

        if (req.getRooms() != null) {
            for (var r : req.getRooms()) {
                adults += r.getAdults();
                children += r.getChildren();
                babies += r.getBabies();

                if (roomIds.length() > 0) {
                    roomIds.append(",");
                    roomNames.append(", ");
                    roomPrices.append(",");

                    roomAdults.append(",");
                    roomChildren.append(",");
                    roomBabies.append(",");
                }

                roomIds.append(r.getRoomId());
                roomNames.append(r.getRoomName());
                roomPrices.append(r.getPricePerPerson());

                roomAdults.append(r.getAdults());
                roomChildren.append(r.getChildren());
                roomBabies.append(r.getBabies());
            }
        }

        res.setAdults(adults);
        res.setChildren(children);
        res.setBabies(babies);

        res.setRoomIds(roomIds.toString());
        res.setRoomNames(roomNames.toString());
        res.setRoomPrices(roomPrices.toString());

        res.setRoomAdults(roomAdults.toString());
        res.setRoomChildren(roomChildren.toString());
        res.setRoomBabies(roomBabies.toString());

        return ResponseEntity.ok(hotelReservationService.createReservation(res));
    }

    @GetMapping("/me")
    public List<ReservationHistoryDTO> myReservations(Principal principal) {
        var user = userRepo.findByEmail(principal.getName()).orElseThrow();

        return hotelReservationService
                .findByUser(user.getId())
                .stream()
                .map(ReservationHistoryDTO::from)
                .toList();
    }

    @GetMapping("/{id}/invoice")
    public ResponseEntity<Map<String, Object>> getInvoiceJson(@PathVariable Long id, Principal principal) {

        var user = userRepo.findByEmail(principal.getName()).orElseThrow();
        HotelReservation r = hotelReservationService.getById(id);

        if (!r.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your reservation");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", r.getId());

        // hotel info
        response.put("hotelId", r.getHotel().getId());
        response.put("hotelName", r.getHotel().getName());
        response.put("hotelCity", r.getHotel().getCity());
        response.put("hotelCountry", r.getHotel().getCountry());

        // dates
        response.put("checkIn", r.getCheckIn());
        response.put("checkOut", r.getCheckOut());
        response.put("createdAt", r.getCreatedAt());

        // guests
        response.put("adults", r.getAdults());
        response.put("children", r.getChildren());
        response.put("babies", r.getBabies());

        // meal + totals
        response.put("mealPlan", r.getMealPlan());
        response.put("totalAmount", r.getTotalAmount());

        // rooms stored as TEXT
        response.put("roomIds", r.getRoomIds());
        response.put("roomNames", r.getRoomNames());
        response.put("roomPrices", r.getRoomPrices());

        response.put("roomAdults", r.getRoomAdults());
        response.put("roomChildren", r.getRoomChildren());
        response.put("roomBabies", r.getRoomBabies());

        response.put("userName", r.getUser().getName());
        response.put("userEmail", r.getUser().getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/invoice.pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id, Principal principal) {

        var user = userRepo.findByEmail(principal.getName()).orElseThrow();
        HotelReservation r = hotelReservationService.getById(id);

        if (!r.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your reservation");
        }

        byte[] pdf = hotelReservationService.generateInvoicePdf(r);

        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=FAC-" + r.getId() + ".pdf")
                .body(pdf);
    }
}
