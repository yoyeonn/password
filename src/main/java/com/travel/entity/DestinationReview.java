package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_reviews")
public class DestinationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double stars;

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getStars() { return stars; }
    public void setStars(double stars) { this.stars = stars; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }
}
