package com.travel.service;

import com.travel.dto.HotelDTO;
import com.travel.entity.Faq;
import com.travel.entity.Hotel;
import com.travel.entity.Nearby;
import com.travel.entity.Programme;
import com.travel.entity.Room;
import com.travel.repository.HotelRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<HotelDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
    return hotelRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
    }

    public List<HotelDTO> search(String keyword) {
    List<Hotel> hotels;
    
    if (keyword == null || keyword.isBlank()) {
        hotels = hotelRepository.findAll();
    } else {
        hotels = hotelRepository.searchHotels(keyword);
    }

    return hotels.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> getSuggestions(String keyword) {
    if (keyword == null || keyword.isBlank()) {
        return List.of();
    }

    String lowerKeyword = keyword.toLowerCase();

    return hotelRepository.findAll().stream()
            .filter(h -> h.getName().toLowerCase().contains(lowerKeyword)
                      || h.getCity().toLowerCase().contains(lowerKeyword)
                      || h.getCountry().toLowerCase().contains(lowerKeyword))
            .map(h -> h.getName() + ", " + h.getCity() + ", " + h.getCountry())
            .distinct()
            .limit(10)
            .collect(Collectors.toList());
}


    private HotelDTO convertToDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setLocation(hotel.getLocation());
        dto.setCity(hotel.getCity());
        dto.setCountry(hotel.getCountry());
        dto.setDays(hotel.getDays());
        dto.setStars(hotel.getStars());
        dto.setAbout(hotel.getAbout());
        dto.setImages(parseJsonList(hotel.getImages()));
        dto.setHighlights(parseJsonList(hotel.getHighlights()));
        dto.setCancellationPolicy(hotel.getCancellationPolicy());
        dto.setAvailableDates(hotel.getAvailableDates());
        dto.setBestTimeToVisit(hotel.getBestTimeToVisit());
        dto.setTravelTips(parseJsonList(hotel.getTravelTips()));
        dto.setSuitedFor(parseJsonList(hotel.getSuitedFor()));
        dto.setIncludes(parseJsonList(hotel.getIncludes()));
        dto.setExcludes(parseJsonList(hotel.getExcludes()));


        dto.setRooms(
            hotel.getRooms() == null ? List.of() :
            hotel.getRooms().stream().map(r -> {
                HotelDTO.RoomDTO room = new HotelDTO.RoomDTO();
                room.setId(r.getId());
                room.setName(r.getName());
                room.setImage(r.getImage());
                room.setDescription(r.getDescription());
                room.setCapacity(r.getCapacity());
                room.setPrice(r.getPrice());
                return room;
            }).collect(Collectors.toList())
        );

        dto.setNearby(
            hotel.getNearby() == null ? List.of() :
            hotel.getNearby().stream().map(n -> {
                HotelDTO.NearbyDTO nearby = new HotelDTO.NearbyDTO();
                nearby.setName(n.getName());
                nearby.setDistance(n.getDistance());
                return nearby;
            }).collect(Collectors.toList())
        );

        dto.setFaq(
            hotel.getFaq() == null ? List.of() :
            hotel.getFaq().stream().map(f -> {
                HotelDTO.FaqDTO faq = new HotelDTO.FaqDTO();
                faq.setQuestion(f.getQuestion());
                faq.setAnswer(f.getAnswer());
                faq.setOpen(f.isOpen());
                return faq;
            }).collect(Collectors.toList())
        );

        dto.setProgramme(
    hotel.getProgramme() == null ? List.of() :
    hotel.getProgramme().stream().map(p -> {
        HotelDTO.ProgrammeDTO prog = new HotelDTO.ProgrammeDTO();
        prog.setDay(p.getDay());
        prog.setActivity(p.getActivity());
        return prog;
    }).collect(Collectors.toList())
);


        return dto;
    }

    // Simple JSON parser for images/highlights
    private List<String> parseJsonList(String json) {
        if (json == null || json.isEmpty()) return List.of();
        return List.of(json.replace("[","").replace("]","").replace("\"","").split(","));
    }

    public HotelDTO updateHotel(Long id, HotelDTO dto) {
    Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

    // 1) basic fields
    hotel.setName(dto.getName());
    hotel.setDescription(dto.getDescription());
    hotel.setLocation(dto.getLocation());
    hotel.setCity(dto.getCity());
    hotel.setCountry(dto.getCountry());
    hotel.setDays(dto.getDays());
    hotel.setStars(dto.getStars());
    hotel.setAbout(dto.getAbout());

    hotel.setCancellationPolicy(dto.getCancellationPolicy());
    hotel.setAvailableDates(dto.getAvailableDates());
    hotel.setBestTimeToVisit(dto.getBestTimeToVisit());

    // 2) JSON string fields
    hotel.setImages(toJsonString(dto.getImages()));
    hotel.setHighlights(toJsonString(dto.getHighlights()));
    hotel.setTravelTips(toJsonString(dto.getTravelTips()));
    hotel.setSuitedFor(toJsonString(dto.getSuitedFor()));
    hotel.setIncludes(toJsonString(dto.getIncludes()));
    hotel.setExcludes(toJsonString(dto.getExcludes()));

    // 3) ROOMS: clear + re-add
    if (hotel.getRooms() == null) hotel.setRooms(new ArrayList<>());
    hotel.getRooms().clear();

    if (dto.getRooms() != null) {
        dto.getRooms().forEach(r -> {
            Room room = new Room();
            room.setName(r.getName());
            room.setImage(r.getImage());
            room.setDescription(r.getDescription());
            room.setCapacity(r.getCapacity());
            room.setPrice(r.getPrice());
            room.setHotel(hotel); // important
            hotel.getRooms().add(room);
        });
    }

    // 4) FAQ: clear + re-add
    if (hotel.getFaq() == null) hotel.setFaq(new ArrayList<>());
    hotel.getFaq().clear();

    if (dto.getFaq() != null) {
        dto.getFaq().forEach(f -> {
            Faq faq = new Faq();
            faq.setQuestion(f.getQuestion());
            faq.setAnswer(f.getAnswer());
            faq.setOpen(f.isOpen());
            faq.setHotel(hotel);
            hotel.getFaq().add(faq);
        });
    }

    // 5) NEARBY: clear + re-add
    if (hotel.getNearby() == null) hotel.setNearby(new ArrayList<>());
    hotel.getNearby().clear();

    if (dto.getNearby() != null) {
        dto.getNearby().forEach(n -> {
            Nearby nb = new Nearby();
            nb.setName(n.getName());
            nb.setDistance(n.getDistance());
            nb.setHotel(hotel);
            hotel.getNearby().add(nb);
        });
    }

    // 6) PROGRAMME: clear + re-add
    if (hotel.getProgramme() == null) hotel.setProgramme(new ArrayList<>());
    hotel.getProgramme().clear();

    if (dto.getProgramme() != null) {
        dto.getProgramme().forEach(p -> {
            Programme prog = new Programme();
            prog.setDay(p.getDay());
            prog.setActivity(p.getActivity());
            prog.setHotel(hotel);
            hotel.getProgramme().add(prog);
        });
    }

    // 7) save
    Hotel saved = hotelRepository.save(hotel);
    return convertToDTO(saved);
}



    private String toJsonString(List<String> list) {
        if (list == null || list.isEmpty()) return "[]";
        // creates: ["a","b","c"]
        return "[\"" + String.join("\",\"", list) + "\"]";
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));
        hotelRepository.delete(hotel);
    }

    public HotelDTO createHotel(HotelDTO dto) {
    Hotel hotel = new Hotel();

    // basic fields
    hotel.setName(dto.getName());
    hotel.setDescription(dto.getDescription());
    hotel.setLocation(dto.getLocation());
    hotel.setCity(dto.getCity());
    hotel.setCountry(dto.getCountry());
    hotel.setDays(dto.getDays());
    hotel.setStars(dto.getStars());
    hotel.setAbout(dto.getAbout());

    hotel.setCancellationPolicy(dto.getCancellationPolicy());
    hotel.setAvailableDates(dto.getAvailableDates());
    hotel.setBestTimeToVisit(dto.getBestTimeToVisit());

    // list fields stored as JSON strings
    hotel.setImages(toJsonString(dto.getImages()));
    hotel.setHighlights(toJsonString(dto.getHighlights()));
    hotel.setTravelTips(toJsonString(dto.getTravelTips()));
    hotel.setSuitedFor(toJsonString(dto.getSuitedFor()));
    hotel.setIncludes(toJsonString(dto.getIncludes()));
    hotel.setExcludes(toJsonString(dto.getExcludes()));

    // rooms
    hotel.setRooms(new ArrayList<>());
    if (dto.getRooms() != null) {
        dto.getRooms().forEach(r -> {
            Room room = new Room();
            room.setName(r.getName());
            room.setImage(r.getImage());
            room.setDescription(r.getDescription());
            room.setCapacity(r.getCapacity());
            room.setPrice(r.getPrice());
            room.setHotel(hotel);
            hotel.getRooms().add(room);
        });
    }

    // faq
    hotel.setFaq(new ArrayList<>());
    if (dto.getFaq() != null) {
        dto.getFaq().forEach(f -> {
            Faq faq = new Faq();
            faq.setQuestion(f.getQuestion());
            faq.setAnswer(f.getAnswer());
            faq.setOpen(f.isOpen());
            faq.setHotel(hotel);
            hotel.getFaq().add(faq);
        });
    }

    // nearby
    hotel.setNearby(new ArrayList<>());
    if (dto.getNearby() != null) {
        dto.getNearby().forEach(n -> {
            Nearby nb = new Nearby();
            nb.setName(n.getName());
            nb.setDistance(n.getDistance());
            nb.setHotel(hotel);
            hotel.getNearby().add(nb);
        });
    }

    // programme
    hotel.setProgramme(new ArrayList<>());
    if (dto.getProgramme() != null) {
        dto.getProgramme().forEach(p -> {
            Programme prog = new Programme();
            prog.setDay(p.getDay());
            prog.setActivity(p.getActivity());
            prog.setHotel(hotel);
            hotel.getProgramme().add(prog);
        });
    }

    Hotel saved = hotelRepository.save(hotel);
    return convertToDTO(saved);
}




}
