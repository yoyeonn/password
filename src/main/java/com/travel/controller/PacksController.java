package com.travel.controller;

import com.travel.dto.PacksUpsertDTO;
import com.travel.dto.PacksDTO;
import com.travel.service.PacksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/packs")
public class PacksController {

    private final PacksService packsService;

    public PacksController(PacksService packsService) {
        this.packsService = packsService;
    }

    // ===== GET ALL =====
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPacks() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<PacksDTO> packs = packsService.getAllPacks();
            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Packs retrieved successfully");
            response.put("data", packs);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("error", "Internal Server Error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ===== GET ONE =====
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPackById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            PacksDTO packs = packsService.getPackById(id);
            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Pack retrieved successfully");
            response.put("data", packs);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Pack not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ===== CREATE =====
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody PacksUpsertDTO payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            PacksDTO created = packsService.createPack(payload);
            response.put("ok", true);
            response.put("status", HttpStatus.CREATED.value());
            response.put("message", "Pack created successfully");
            response.put("data", created);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", ex.getMessage());
            response.put("error", "Bad Request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody PacksUpsertDTO payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            PacksDTO updated = packsService.updatePack(id, payload);
            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Pack updated successfully");
            response.put("data", updated);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Pack not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        } catch (Exception ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", ex.getMessage());
            response.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            packsService.deletePack(id);
            response.put("ok", true);
            response.put("status", HttpStatus.OK.value());
            response.put("message", "Pack deleted successfully");
            response.put("data", null);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            response.put("ok", false);
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", ex.getMessage());
            response.put("error", "Pack not found");
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
