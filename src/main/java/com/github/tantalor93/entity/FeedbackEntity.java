package com.github.tantalor93.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FeedbackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String feedback;

    public FeedbackEntity(String name, String email, String feedback) {
        this.name = name;
        this.email = email;
        this.feedback = feedback;
    }

    //for JPA
    protected FeedbackEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFeedback() {
        return feedback;
    }
}
