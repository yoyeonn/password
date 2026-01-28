package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packs_reviews")
public class PacksReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double stars;

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "packs_id")
    private Packs packs;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getStars() { return stars; }
    public void setStars(double stars) { this.stars = stars; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Packs getPacks() { return packs; }
    public void setPacks(Packs packs) { this.packs = packs; }
}
