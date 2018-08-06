package com.github.tantalor93.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tantalor93.rest.FeedbacksController;
import org.springframework.hateoas.ResourceSupport;

import java.util.Collections;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * DTO representing list of feedbacks
 */
public class FeedbacksResource extends ResourceSupport {

    @JsonProperty("_embedded")
    private List<FeedbackResource> feedbacks;

    public FeedbacksResource(List<FeedbackResource> feedbacks) {
        this.feedbacks = feedbacks;
        add(linkTo(methodOn(FeedbacksController.class).getFeedbacks()).withSelfRel());

    }

    // needed for webflux
    protected FeedbacksResource() {
    }

    public List<FeedbackResource> getFeedbacks() {
        return Collections.unmodifiableList(feedbacks);
    }

    @Override
    public String toString() {
        return "FeedbacksResource{" +
                "feedbacks=" + feedbacks +
                '}';
    }
}
