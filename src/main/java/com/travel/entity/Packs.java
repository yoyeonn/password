package com.travel.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packs")
public class Packs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int days;

    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String about;

    @Column(length = 2000)
    private String images;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksActivity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksFAQ> faq = new ArrayList<>();

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksReview> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksNearby> nearby = new ArrayList<>();

    // ===== getters/setters =====

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

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

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }

    public List<PacksActivity> getActivities() { return activities; }
    public void setActivities(List<PacksActivity> items) {
        this.activities.clear();
        if (items != null) {
            for (PacksActivity a : items) {
                a.setPacks(this);
                this.activities.add(a);
            }
        }
    }

    public List<PacksFAQ> getFaq() { return faq; }
    public void setFaq(List<PacksFAQ> items) {
        this.faq.clear();
        if (items != null) {
            for (PacksFAQ f : items) {
                f.setPacks(this);
                this.faq.add(f);
            }
        }
    }

    public List<PacksNearby> getNearby() { return nearby; }
    public void setNearby(List<PacksNearby> items) {
        this.nearby.clear();
        if (items != null) {
            for (PacksNearby n : items) {
                n.setPacks(this);
                this.nearby.add(n);
            }
        }
    }

    public List<PacksReview> getReviews() { return reviews; }
    public void setReviews(List<PacksReview> reviews) {
        this.reviews.clear();
        if (reviews != null) {
            for (PacksReview r : reviews) {
                r.setPacks(this);
                this.reviews.add(r);
            }
        }
    }
}
