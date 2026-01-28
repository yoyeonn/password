package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packs_faq")
public class PacksFAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "packs_id")
    private Packs packs;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Packs getPacks() { return packs; }
    public void setPacks(Packs packs) { this.packs = packs; }
}
