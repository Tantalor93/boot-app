package com.github.tantalor93.dto;

import java.util.Collections;
import java.util.List;

/**
 * DTO representing list of feedbacks
 */
public class Feedbacks {
    private final List<Feedback> feedbacks;

    public Feedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Feedback> getFeedbacks() {
        return Collections.unmodifiableList(feedbacks);
    }

    @Override
    public String toString() {
        return "Feedbacks{" +
                "feedbacks=" + feedbacks +
                '}';
    }
}
