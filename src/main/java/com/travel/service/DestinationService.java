package com.travel.service;

import com.travel.dto.DestinationDTO;
import com.travel.entity.*;
import com.travel.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // ---------------------------
    // READ
    // ---------------------------
    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO getDestinationById(Long id) {
        Destination d = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        return convertToDTO(d);
    }

    public List<DestinationDTO> search(String q, Double maxPrice, LocalDate checkIn, LocalDate checkOut) {
        return destinationRepository.search(q, maxPrice, checkIn, checkOut)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------------------
    // CREATE
    // ---------------------------
    public DestinationDTO createDestination(DestinationDTO dto) {
        Destination d = new Destination();

        // basic fields
        d.setName(dto.getName());
        d.setCountry(dto.getCountry());
        d.setPrice(dto.getPrice());
        d.setDays(dto.getDays());
        d.setLocation(dto.getLocation());
        d.setTitle(dto.getTitle());
        d.setDescription(dto.getDescription());
        d.setAbout(dto.getAbout());

        // dates
        d.setAvailableFrom(parseDate(dto.getAvailableFrom()));
        d.setAvailableTo(parseDate(dto.getAvailableTo()));

        // images (store as comma-separated)
        d.setImages(listToCsv(dto.getImages()));

        // children
        applyChildrenFromDTO(d, dto);

        Destination saved = destinationRepository.save(d);
        return convertToDTO(saved);
    }

    // ---------------------------
    // UPDATE
    // ---------------------------
    public DestinationDTO updateDestination(Long id, DestinationDTO dto) {
        Destination d = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));

        // basic fields
        d.setName(dto.getName());
        d.setCountry(dto.getCountry());
        d.setPrice(dto.getPrice());
        d.setDays(dto.getDays());
        d.setLocation(dto.getLocation());
        d.setTitle(dto.getTitle());
        d.setDescription(dto.getDescription());
        d.setAbout(dto.getAbout());

        // dates
        d.setAvailableFrom(parseDate(dto.getAvailableFrom()));
        d.setAvailableTo(parseDate(dto.getAvailableTo()));

        // images
        d.setImages(listToCsv(dto.getImages()));

        // replace children (orphanRemoval=true handles delete)
        applyChildrenFromDTO(d, dto);

        Destination saved = destinationRepository.save(d);
        return convertToDTO(saved);
    }

    // ---------------------------
    // DELETE
    // ---------------------------
    public void deleteDestination(Long id) {
        Destination d = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
        destinationRepository.delete(d);
    }

    // ---------------------------
    // Helpers: children update
    // ---------------------------
    private void applyChildrenFromDTO(Destination d, DestinationDTO dto) {

        // Activities
        if (d.getActivities() == null) d.setActivities(new ArrayList<>());
        else d.getActivities().clear();
        if (dto.getActivities() != null) {
            for (DestinationDTO.ActivityDTO a : dto.getActivities()) {
                DestinationActivity act = new DestinationActivity();
                act.setDay(a.getDay());
                act.setActivity(a.getActivity());
                act.setDestination(d);
                d.getActivities().add(act);
            }
        }

        // FAQ
        if (d.getFaq() == null) d.setFaq(new ArrayList<>());
        else d.getFaq().clear();
        if (dto.getFaq() != null) {
            for (DestinationDTO.FaqDTO f : dto.getFaq()) {
                DestinationFAQ faq = new DestinationFAQ();
                faq.setQuestion(f.getQuestion());
                faq.setAnswer(f.getAnswer());
                faq.setDestination(d);
                d.getFaq().add(faq);
            }
        }

        // Nearby
        if (d.getNearby() == null) d.setNearby(new ArrayList<>());
        else d.getNearby().clear();
        if (dto.getNearby() != null) {
            for (DestinationDTO.NearbyDTO n : dto.getNearby()) {
                DestinationNearby nb = new DestinationNearby();
                nb.setName(n.getName());
                nb.setDistance(n.getDistance());
                nb.setDestination(d);
                d.getNearby().add(nb);
            }
        }

        // Reviews (assuming your DestinationReview has: name, stars, comment, destination)
        if (d.getReviews() == null) d.setReviews(new ArrayList<>());
        else d.getReviews().clear();
        if (dto.getReviews() != null) {
            for (DestinationDTO.ReviewDTO r : dto.getReviews()) {
                DestinationReview rev = new DestinationReview();
                rev.setName(r.getName());
                rev.setStars(r.getStars());
                rev.setComment(r.getComment());
                rev.setDestination(d);
                d.getReviews().add(rev);
            }
        }
    }

    // ---------------------------
    // Helpers: conversion
    // ---------------------------
    private DestinationDTO convertToDTO(Destination d) {
        DestinationDTO dto = new DestinationDTO();

        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setCountry(d.getCountry());
        dto.setPrice(d.getPrice());
        dto.setDays(d.getDays());
        dto.setLocation(d.getLocation());
        dto.setTitle(d.getTitle());
        dto.setDescription(d.getDescription());
        dto.setAbout(d.getAbout());

        dto.setAvailableFrom(d.getAvailableFrom() != null ? d.getAvailableFrom().toString() : null);
        dto.setAvailableTo(d.getAvailableTo() != null ? d.getAvailableTo().toString() : null);

        dto.setImages(csvToList(d.getImages()));

        dto.setActivities(d.getActivities() == null ? List.of() :
                d.getActivities().stream().map(a -> {
                    DestinationDTO.ActivityDTO x = new DestinationDTO.ActivityDTO();
                    x.setDay(a.getDay());
                    x.setActivity(a.getActivity());
                    return x;
                }).collect(Collectors.toList())
        );

        dto.setFaq(d.getFaq() == null ? List.of() :
                d.getFaq().stream().map(f -> {
                    DestinationDTO.FaqDTO x = new DestinationDTO.FaqDTO();
                    x.setQuestion(f.getQuestion());
                    x.setAnswer(f.getAnswer());
                    x.setOpen(false);
                    return x;
                }).collect(Collectors.toList())
        );

        dto.setNearby(d.getNearby() == null ? List.of() :
                d.getNearby().stream().map(n -> {
                    DestinationDTO.NearbyDTO x = new DestinationDTO.NearbyDTO();
                    x.setName(n.getName());
                    x.setDistance(n.getDistance());
                    return x;
                }).collect(Collectors.toList())
        );

        dto.setReviews(d.getReviews() == null ? List.of() :
                d.getReviews().stream().map(r -> {
                    DestinationDTO.ReviewDTO x = new DestinationDTO.ReviewDTO();
                    x.setName(r.getName());
                    x.setStars(r.getStars());
                    x.setComment(r.getComment());
                    return x;
                }).collect(Collectors.toList())
        );

        return dto;
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.trim().isEmpty()) return null;
        return LocalDate.parse(s.trim()); // expects yyyy-MM-dd
    }

    private String listToCsv(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        return String.join(",", list);
    }

    private List<String> csvToList(String csv) {
        if (csv == null || csv.trim().isEmpty()) return List.of();
        return List.of(csv.split("\\s*,\\s*"));
    }
}
