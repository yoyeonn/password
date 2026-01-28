package com.travel.controller;

import com.travel.dto.DestinationDTO;
import com.travel.service.DestinationService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDestinations() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<DestinationDTO> destinations = destinationService.getAllDestinations();

            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Destinations retrieved successfully");
            response.put("data", destinations);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Internal Server Error");
            response.put("message", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDestinationById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            DestinationDTO destination = destinationService.getDestinationById(id);

            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Destination retrieved successfully");
            response.put("data", destination);

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            // Not Found
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Destination not found");

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            // Server Error
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(
    @RequestParam(required = false) String q,
    @RequestParam(required = false) Double maxPrice,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut
    ) {
    Map<String, Object> response = new HashMap<>();

    try {
    // Optional validation (recommended)
    if (checkIn != null && checkOut != null && checkOut.isBefore(checkIn)) {
      response.put("ok", false);
      response.put("status", 400);
      response.put("message", "checkOut must be after checkIn");
      return ResponseEntity.badRequest().body(response);
    }

    List<DestinationDTO> results = destinationService.search(q, maxPrice, checkIn, checkOut);

    response.put("ok", true);
    response.put("status", 200);
    response.put("message", "Search results");
    response.put("data", results);
    return ResponseEntity.ok(response);

  } catch (Exception ex) {
    response.put("ok", false);
    response.put("status", 500);
    response.put("message", ex.getMessage());
    return ResponseEntity.status(500).body(response);
  }

}

@PostMapping
public ResponseEntity<Map<String, Object>> createDestination(@RequestBody DestinationDTO dto) {
    Map<String, Object> response = new HashMap<>();
    try {
        DestinationDTO created = destinationService.createDestination(dto);

        response.put("ok", true);
        response.put("status", HttpStatus.CREATED.value());
        response.put("message", "Destination created successfully");
        response.put("data", created);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    } catch (Exception ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        response.put("error", "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

@PutMapping("/{id}")
public ResponseEntity<Map<String, Object>> updateDestination(
        @PathVariable Long id,
        @RequestBody DestinationDTO dto
) {
    Map<String, Object> response = new HashMap<>();
    try {
        DestinationDTO updated = destinationService.updateDestination(id, dto);

        response.put("ok", true);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Destination updated successfully");
        response.put("data", updated);

        return ResponseEntity.ok(response);

    } catch (RuntimeException ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", ex.getMessage());
        response.put("error", "Destination not found");
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
public ResponseEntity<Map<String, Object>> deleteDestination(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();
    try {
        destinationService.deleteDestination(id);

        response.put("ok", true);
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Destination deleted successfully");

        return ResponseEntity.ok(response);

    } catch (RuntimeException ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("message", ex.getMessage());
        response.put("error", "Destination not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    } catch (Exception ex) {
        response.put("ok", false);
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());
        response.put("error", "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

}
