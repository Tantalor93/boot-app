package com.github.tantalor93.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * DTO representing feedback of user
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("feedback")
public class Feedback {

    private Long id;
    private String name;
    private String email;
    private String feedback;

    public Feedback(final Long id,
                    final String name,
                    final String email,
                    final String feedback) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.feedback = feedback;
    }

    // needed for model mapper
    protected Feedback() {
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

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
