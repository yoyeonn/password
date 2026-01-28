package com.travel.controller;

import com.travel.dto.HotelDTO;
import com.travel.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getHotels() {
        try {
            List<HotelDTO> hotels = hotelService.getAllHotels();

            Map<String, Object> response = new HashMap<>();
            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Hotels retrieved successfully");
            response.put("data", hotels);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("ok", false);
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getHotelById(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();

    try {
        HotelDTO hotel = hotelService.getHotelById(id);

        response.put("ok", true);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Hotel retrieved successfully");
        response.put("data", hotel);

        return ResponseEntity.ok(response);

    } catch (RuntimeException ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", ex.getMessage());
        response.put("error", "Hotel not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    } catch (Exception ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        response.put("error", "Internal Server Error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword) {
        List<HotelDTO> hotels = hotelService.search(keyword);
        return ResponseEntity.ok(Map.of("data", hotels));
    }

    @GetMapping("/suggestions")
    public List<String> getSuggestions(@RequestParam String keyword) {
        return hotelService.getSuggestions(keyword);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateHotel(
            @PathVariable Long id,
            @RequestBody HotelDTO hotelDTO
    ) {
        Map<String, Object> response = new HashMap<>();

        try {
            HotelDTO updated = hotelService.updateHotel(id, hotelDTO);

            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Hotel updated successfully");
            response.put("data", updated);

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Hotel not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteHotel(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            hotelService.deleteHotel(id);

            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Hotel deleted successfully");

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Hotel not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createHotel(@RequestBody HotelDTO hotelDTO) {
        try {
            HotelDTO created = hotelService.createHotel(hotelDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("ok", true);
            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "Hotel created successfully");
            response.put("data", created);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception ex) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("ok", false);
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
