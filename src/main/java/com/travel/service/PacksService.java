package com.travel.service;

import com.travel.dto.DestinationDTO;
import com.travel.dto.HotelDTO;
import com.travel.dto.PacksDTO;
import com.travel.dto.PacksUpsertDTO;
import com.travel.entity.*;
import com.travel.repository.DestinationRepository;
import com.travel.repository.HotelRepository;
import com.travel.repository.PacksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacksService {

    private final PacksRepository packsRepository;
    private final HotelRepository hotelRepository;
    private final DestinationRepository destinationRepository;

    public PacksService(
            PacksRepository packsRepository,
            HotelRepository hotelRepository,
            DestinationRepository destinationRepository
    ) {
        this.packsRepository = packsRepository;
        this.hotelRepository = hotelRepository;
        this.destinationRepository = destinationRepository;
    }

    // ===== GET ALL =====
    public List<PacksDTO> getAllPacks() {
        return packsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ===== GET ONE =====
    public PacksDTO getPackById(Long id) {
        Packs pack = packsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + id));
        return convertToDTO(pack);
    }

    // ===== CREATE =====
    @Transactional
    public PacksDTO createPack(PacksUpsertDTO payload) {
        Packs pack = new Packs();
        applyUpsert(pack, payload);
        Packs saved = packsRepository.save(pack);
        return convertToDTO(saved);
    }

    // ===== UPDATE =====
    @Transactional
    public PacksDTO updatePack(Long id, PacksUpsertDTO payload) {
        Packs pack = packsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + id));

        applyUpsert(pack, payload);
        Packs saved = packsRepository.save(pack);
        return convertToDTO(saved);
    }

    // ===== DELETE =====
    @Transactional
    public void deletePack(Long id) {
        if (!packsRepository.existsById(id)) {
            throw new RuntimeException("Pack not found with id: " + id);
        }
        packsRepository.deleteById(id);
    }

    // ===== Apply Create/Update payload to entity =====
    private void applyUpsert(Packs pack, PacksUpsertDTO payload) {

        pack.setName(payload.getName());
        pack.setPrice(payload.getPrice());
        pack.setDays(payload.getDays());
        pack.setDescription(payload.getDescription());
        pack.setAbout(payload.getAbout());

        // images list => comma string
        if (payload.getImages() != null && !payload.getImages().isEmpty()) {
            pack.setImages(String.join(",", payload.getImages()));
        } else {
            pack.setImages(null);
        }

        // hotel by id
        if (payload.getHotelId() != null) {
            Hotel h = hotelRepository.findById(payload.getHotelId())
                    .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + payload.getHotelId()));
            pack.setHotel(h);
        } else {
            pack.setHotel(null);
        }

        // ✅ destination by id (country/location will come from destination later)
        if (payload.getDestinationId() != null) {
            Destination d = destinationRepository.findById(payload.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found with id: " + payload.getDestinationId()));
            pack.setDestination(d);
        } else {
            pack.setDestination(null);
        }

        // Activities
        pack.getActivities().clear();
        if (payload.getActivities() != null) {
            for (PacksUpsertDTO.ActivityDTO a : payload.getActivities()) {
                PacksActivity pa = new PacksActivity();
                pa.setDay(a.getDay());
                pa.setActivity(a.getActivity());
                pa.setPacks(pack);
                pack.getActivities().add(pa);
            }
        }

        // FAQ
        pack.getFaq().clear();
        if (payload.getFaq() != null) {
            for (PacksUpsertDTO.FaqDTO f : payload.getFaq()) {
                PacksFAQ pf = new PacksFAQ();
                pf.setQuestion(f.getQuestion());
                pf.setAnswer(f.getAnswer());
                pf.setPacks(pack);
                pack.getFaq().add(pf);
            }
        }

        // Nearby
        pack.getNearby().clear();
        if (payload.getNearby() != null) {
            for (PacksUpsertDTO.NearbyDTO n : payload.getNearby()) {
                PacksNearby pn = new PacksNearby();
                pn.setName(n.getName());
                pn.setDistance(n.getDistance());
                pn.setPacks(pack);
                pack.getNearby().add(pn);
            }
        }
    }

    // ===== ENTITY -> DTO =====
    private PacksDTO convertToDTO(Packs pack) {
        PacksDTO dto = new PacksDTO();

        dto.setId(pack.getId());
        dto.setName(pack.getName());
        dto.setPrice(pack.getPrice());
        dto.setDays(pack.getDays());
        dto.setDescription(pack.getDescription());
        dto.setAbout(pack.getAbout());

        // ✅ country + location always from destination
        if (pack.getDestination() != null) {
            dto.setCountry(pack.getDestination().getCountry());
            dto.setLocation(pack.getDestination().getLocation());
        }

        dto.setImages(pack.getImages() != null ? List.of(pack.getImages().split(",")) : List.of());

        dto.setActivities(
                pack.getActivities().stream().map(a -> {
                    PacksDTO.ActivityDTO act = new PacksDTO.ActivityDTO();
                    act.setDay(a.getDay());
                    act.setActivity(a.getActivity());
                    return act;
                }).collect(Collectors.toList())
        );

        dto.setFaq(
                pack.getFaq().stream().map(f -> {
                    PacksDTO.FaqDTO faq = new PacksDTO.FaqDTO();
                    faq.setQuestion(f.getQuestion());
                    faq.setAnswer(f.getAnswer());
                    faq.setOpen(false);
                    return faq;
                }).collect(Collectors.toList())
        );

        dto.setNearby(
                pack.getNearby().stream().map(n -> {
                    PacksDTO.NearbyDTO near = new PacksDTO.NearbyDTO();
                    near.setName(n.getName());
                    near.setDistance(n.getDistance());
                    return near;
                }).collect(Collectors.toList())
        );

        // Hotel (avoid map inference problem by using HotelDTO.RoomDTO explicitly)
        if (pack.getHotel() != null) {
            Hotel h = pack.getHotel();

            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setId(h.getId());
            hotelDTO.setName(h.getName());
            hotelDTO.setDescription(h.getDescription());
            hotelDTO.setStars(h.getStars());
            hotelDTO.setImages(h.getImages() != null ? List.of(h.getImages().split(",")) : List.of());

            hotelDTO.setRooms(
                    h.getRooms() == null ? List.of() :
                            h.getRooms().stream().map(r -> {
                                HotelDTO.RoomDTO rd = new HotelDTO.RoomDTO();
                                rd.setId(r.getId());
                                rd.setName(r.getName());
                                rd.setPrice(r.getPrice());
                                rd.setCapacity(r.getCapacity());
                                rd.setDescription(r.getDescription());
                                rd.setImage(r.getImage());
                                return rd;
                            }).collect(Collectors.toList())
            );

            dto.setHotel(hotelDTO);
        }

        // Destination DTO
        if (pack.getDestination() != null) {
            Destination d = pack.getDestination();

            DestinationDTO destDTO = new DestinationDTO();
            destDTO.setId(d.getId());
            destDTO.setName(d.getName());
            destDTO.setCountry(d.getCountry());
            destDTO.setLocation(d.getLocation());
            destDTO.setDescription(d.getDescription());
            destDTO.setImages(d.getImages() != null ? List.of(d.getImages().split(",")) : List.of());

            dto.setDestination(destDTO);
        }

        return dto;
    }
}
