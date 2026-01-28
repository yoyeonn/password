package com.travel.dto;

import java.util.List;

public class PacksDTO {
    private Long id;
    private String name;

    // ✅ keep these for frontend, BUT they will come from Destination
    private String country;
    private String location;

    private double price;
    private int days;
    private double rating;
    private String description;
    private String about;
    private List<String> images;

    private HotelDTO hotel;
    private DestinationDTO destination;

    private List<ActivityDTO> activities;
    private List<FaqDTO> faq;
    private List<ReviewDTO> reviews;
    private List<NearbyDTO> nearby;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public HotelDTO getHotel() { return hotel; }
    public void setHotel(HotelDTO hotel) { this.hotel = hotel; }

    public DestinationDTO getDestination() { return destination; }
    public void setDestination(DestinationDTO destination) { this.destination = destination; }

    public List<ActivityDTO> getActivities() { return activities; }
    public void setActivities(List<ActivityDTO> activities) { this.activities = activities; }

    public List<FaqDTO> getFaq() { return faq; }
    public void setFaq(List<FaqDTO> faq) { this.faq = faq; }

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }

    public List<NearbyDTO> getNearby() { return nearby; }
    public void setNearby(List<NearbyDTO> nearby) { this.nearby = nearby; }

    // ===== nested DTOs =====

    public static class ActivityDTO {
        private int day;
        private String activity;
        public int getDay() { return day; }
        public void setDay(int day) { this.day = day; }
        public String getActivity() { return activity; }
        public void setActivity(String activity) { this.activity = activity; }
    }

    public static class FaqDTO {
        private String question;
        private String answer;
        private boolean open;
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
        public boolean isOpen() { return open; }
        public void setOpen(boolean open) { this.open = open; }
    }

    public static class ReviewDTO {
        private String name;
        private double stars;
        private String comment;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getStars() { return stars; }
        public void setStars(double stars) { this.stars = stars; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }

    public static class NearbyDTO {
        private String name;
        private String distance;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDistance() { return distance; }
        public void setDistance(String distance) { this.distance = distance; }
    }
}
