package com.travel.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.travel.dto.HotelReservationDTO;
import com.travel.entity.HotelReservation;
import com.travel.repository.HotelReservationRepository;

@Service
public class HotelReservationService {

    private final HotelReservationRepository repo;

    public HotelReservationService(HotelReservationRepository repo) {
        this.repo = repo;
    }

    public HotelReservation createReservation(HotelReservation r) {
        return repo.save(r);
    }

    public List<HotelReservation> findByUser(Long userId) {
        return repo.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    public HotelReservation getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ✅ ADMIN: list all (DTO)
    public List<HotelReservationDTO> getAllAdmin() {
        return repo.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ ADMIN: one (DTO)
    public HotelReservationDTO getByIdAdmin(Long id) {
        HotelReservation r = repo.findById(id).orElseThrow();
        return toDTO(r);
    }

    private HotelReservationDTO toDTO(HotelReservation r) {
        HotelReservationDTO dto = new HotelReservationDTO();
        dto.setId(r.getId());

        if (r.getUser() != null) {
            dto.setUserId(r.getUser().getId());
            dto.setUserName(r.getUser().getName());
            dto.setUserEmail(r.getUser().getEmail());
        }

        if (r.getHotel() != null) {
            dto.setHotelId(r.getHotel().getId());
            dto.setHotelName(r.getHotel().getName());
        }

        dto.setCheckIn(r.getCheckIn());
        dto.setCheckOut(r.getCheckOut());
        dto.setAdults(r.getAdults());
        dto.setChildren(r.getChildren());
        dto.setBabies(r.getBabies());
        dto.setMealPlan(r.getMealPlan());
        dto.setTotalAmount(r.getTotalAmount());
        dto.setCreatedAt(r.getCreatedAt());

        // parse roomNames (stored as TEXT)
        if (r.getRoomNames() != null && !r.getRoomNames().isBlank()) {
            List<String> rooms = Arrays.stream(r.getRoomNames().split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toList());
            dto.setRoomNames(rooms);
        } else {
            dto.setRoomNames(List.of());
        }

        return dto;
    }

    /**
     * SERVER GENERATED PDF (simple version).
     * Requires OpenPDF dependency.
     */
    public byte[] generateInvoicePdf(HotelReservation r) {
        try {
            com.lowagie.text.Document doc = new com.lowagie.text.Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            com.lowagie.text.pdf.PdfWriter.getInstance(doc, out);

            doc.open();

            var fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            doc.add(new com.lowagie.text.Paragraph("FACTURE - Reservation Hotel"));
            doc.add(new com.lowagie.text.Paragraph("Facture N°: FAC-" + r.getId()));
            doc.add(new com.lowagie.text.Paragraph("Date: " + r.getCreatedAt().toLocalDate().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph(" "));
            doc.add(new com.lowagie.text.Paragraph("Client: " + r.getUser().getName() + " (" + r.getUser().getEmail() + ")"));
            doc.add(new com.lowagie.text.Paragraph("Hotel: " + r.getHotel().getName()));
            doc.add(new com.lowagie.text.Paragraph("Check-in: " + r.getCheckIn().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph("Check-out: " + r.getCheckOut().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph("Formule: " + r.getMealPlan()));
            doc.add(new com.lowagie.text.Paragraph("Chambres: " + (r.getRoomNames() == null ? "-" : r.getRoomNames())));
            doc.add(new com.lowagie.text.Paragraph("Montant total: " + r.getTotalAmount() + " TND"));

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
