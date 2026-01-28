package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_activities")
public class DestinationActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int day;
    private String activity;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }
}
