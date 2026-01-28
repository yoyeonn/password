package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packs_nearby")
public class PacksNearby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String distance;

    @ManyToOne
    @JoinColumn(name = "packs_id")
    private Packs packs;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public Packs getPacks() { return packs; }
    public void setPacks(Packs packs) { this.packs = packs; }
}

