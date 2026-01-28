package com.travel.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "destination")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private double price;
    private int days;
    private String location;
    private String title;
    private LocalDate availableFrom;
    
    public LocalDate getAvailableFrom() {
        return availableFrom;
    }
    public void setAvailableFrom(LocalDate availableFrom) {
        this.availableFrom = availableFrom;
    }
    public LocalDate getAvailableTo() {
        return availableTo;
    }
    public void setAvailableTo(LocalDate availableTo) {
        this.availableTo = availableTo;
    }
    private LocalDate availableTo;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String about;

    @Column(length = 2000)
    private String images;


    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationActivity> activities;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationFAQ> faq;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationReview> reviews;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DestinationNearby> nearby;




    // Getters & Setters below
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

    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }

    public List<DestinationActivity> getActivities() { return activities; }
    public void setActivities(List<DestinationActivity> activities) { this.activities = activities; }

    public List<DestinationFAQ> getFaq() { return faq; }
    public void setFaq(List<DestinationFAQ> faq) { this.faq = faq; }

    public List<DestinationReview> getReviews() { return reviews; }
    public void setReviews(List<DestinationReview> reviews) { this.reviews = reviews; }

    public List<DestinationNearby> getNearby() { return nearby; }
    public void setNearby(List<DestinationNearby> nearby) { this.nearby = nearby; }
}
