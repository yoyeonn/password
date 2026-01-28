package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_faq")
public class DestinationFAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }
}
