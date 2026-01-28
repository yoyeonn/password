package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_nearby")
public class DestinationNearby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String distance;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDistance() { return distance; }
    public void setDistance(String distance) { this.distance = distance; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }
}
