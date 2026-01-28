package com.travel.dto;

import java.util.List;

public class HotelDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String city;
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
    private String days;
    private String stars;
    private String about;
    private String cancellationPolicy;
    private String availableDates;
    private String bestTimeToVisit;
    private List<String> travelTips;
    private List<String> suitedFor;
    private List<String> includes;
    private List<String> excludes;
    private List<String> images;
    private List<String> highlights;
    private List<RoomDTO> rooms;
    private List<NearbyDTO> nearby;
    private List<FaqDTO> faq;
    private List<ProgrammeDTO> programme;

    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    public String getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(String availableDates) {
        this.availableDates = availableDates;
    }

    public String getBestTimeToVisit() {
        return bestTimeToVisit;
    }

    public void setBestTimeToVisit(String bestTimeToVisit) {
        this.bestTimeToVisit = bestTimeToVisit;
    }

    public List<String> getTravelTips() {
        return travelTips;
    }

    public void setTravelTips(List<String> travelTips) {
        this.travelTips = travelTips;
    }

    public List<String> getSuitedFor() {
        return suitedFor;
    }

    public void setSuitedFor(List<String> suitedFor) {
        this.suitedFor = suitedFor;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public void setIncludes(List<String> includes) {
        this.includes = includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    

    // getters and setters

    public List<ProgrammeDTO> getProgramme() {
        return programme;
    }

    public void setProgramme(List<ProgrammeDTO> programme) {
        this.programme = programme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public List<NearbyDTO> getNearby() {
        return nearby;
    }

    public void setNearby(List<NearbyDTO> nearby) {
        this.nearby = nearby;
    }

    public List<FaqDTO> getFaq() {
        return faq;
    }

    public void setFaq(List<FaqDTO> faq) {
        this.faq = faq;
    }

    public static class RoomDTO {
        private Long id;
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        private String name;
        private String image;
        private String description;
        private int capacity;
        private double price;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public int getCapacity() {
            return capacity;
        }
        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }
        public double getPrice() {
            return price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        
        // getters and setters
    }

    public static class NearbyDTO {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDistance() {
            return distance;
        }
        public void setDistance(String distance) {
            this.distance = distance;
        }
        private String distance;
        // getters and setters
    }

    public static class FaqDTO {
        private String question;
        public String getQuestion() {
            return question;
        }
        public void setQuestion(String question) {
            this.question = question;
        }
        public String getAnswer() {
            return answer;
        }
        public void setAnswer(String answer) {
            this.answer = answer;
        }
        public boolean isOpen() {
            return open;
        }
        public void setOpen(boolean open) {
            this.open = open;
        }
        private String answer;
        private boolean open;
        // getters and setters
    }

    public class HighlightDTO {
        private String name;
        private Integer count;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }
    }

    public static class ProgrammeDTO {
        private int day;
        private String activity;

        public int getDay() { return day; }
        public void setDay(int day) { this.day = day; }

        public String getActivity() { return activity; }
        public void setActivity(String activity) { this.activity = activity; }
    }
}
