package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packs_activities")
public class PacksActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int day;
    private String activity;

    @ManyToOne
    @JoinColumn(name = "packs_id")
    private Packs packs;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public String getActivity() { return activity; }
    public void setActivity(String activity) { this.activity = activity; }

    public Packs getPacks() { return packs; }
    public void setPacks(Packs packs) { this.packs = packs; }
}
