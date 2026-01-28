package com.travel.dto;

import java.util.List;

public class PacksUpsertDTO {
    private String name;
    private double price;
    private int days;
    private String location;
    private String description;
    private String about;
    private List<String> images;

    private Long hotelId;
    private Long destinationId;

    private List<ActivityDTO> activities;
    private List<FaqDTO> faq;
    private List<NearbyDTO> nearby;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAbout() { return about; }
    public void setAbout(String about) { this.about = about; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }

    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

    public List<ActivityDTO> getActivities() { return activities; }
    public void setActivities(List<ActivityDTO> activities) { this.activities = activities; }

    public List<FaqDTO> getFaq() { return faq; }
    public void setFaq(List<FaqDTO> faq) { this.faq = faq; }

    public List<NearbyDTO> getNearby() { return nearby; }
    public void setNearby(List<NearbyDTO> nearby) { this.nearby = nearby; }

    // nested
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

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }

        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
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
