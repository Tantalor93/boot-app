package com.github.tantalor93.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tantalor93.rest.FeedbacksController;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class FeedbackResource extends ResourceSupport {
    private final Feedback feedback;

    @JsonCreator
    public FeedbackResource(@JsonProperty("feedback") Feedback feedback) {
        this.feedback = feedback;
        add(linkTo(methodOn(FeedbacksController.class).getFeedbacks()).withRel("feedbacks"));
        add(linkTo(methodOn(FeedbacksController.class).getFeedback(feedback.getId())).withSelfRel());
    }

    public Feedback getFeedback() {
        return feedback;
    }
}
