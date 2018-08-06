package com.github.tantalor93.dto;

import java.util.Objects;

/**
 * DTO representing feedback of user
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback1 = (Feedback) o;
        return Objects.equals(id, feedback1.id) &&
                Objects.equals(name, feedback1.name) &&
                Objects.equals(email, feedback1.email) &&
                Objects.equals(feedback, feedback1.feedback);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, email, feedback);
    }
}
