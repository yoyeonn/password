package com.travel.dto;

import java.util.List;

public class DestinationDTO {

    private Long id;
    private String name;
    private String country;
    private double price;
    private int days;

    private String description;
    private String about;

    private String location;
    private String title;

    private String availableFrom;
    private String availableTo;

    private List<String> images;

    private List<ActivityDTO> activities;
    private List<FaqDTO> faq;
    private List<ReviewDTO> reviews;
    private List<NearbyDTO> nearby;

    // ---- getters/setters (MAIN) ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAvailableFrom() { return availableFrom; }
    public void setAvailableFrom(String availableFrom) { this.availableFrom = availableFrom; }

    public String getAvailableTo() { return availableTo; }
    public void setAvailableTo(String availableTo) { this.availableTo = availableTo; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<ActivityDTO> getActivities() { return activities; }
    public void setActivities(List<ActivityDTO> activities) { this.activities = activities; }

    public List<FaqDTO> getFaq() { return faq; }
    public void setFaq(List<FaqDTO> faq) { this.faq = faq; }

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }

    public List<NearbyDTO> getNearby() { return nearby; }
    public void setNearby(List<NearbyDTO> nearby) { this.nearby = nearby; }

    // ---- nested DTOs ----
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
        private Boolean open = false;
        public Boolean getOpen() { return open; }
        public void setOpen(Boolean open) { this.open = open; }
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
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
