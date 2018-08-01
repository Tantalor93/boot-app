package com.github.tantalor93.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tantalor93.dto.Feedback;
import com.github.tantalor93.dto.FeedbackToCreate;
import com.github.tantalor93.dto.Feedbacks;
import com.github.tantalor93.service.FeedbacksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Resource for {@link Feedback}
 */
@RestController
public class FeedbacksResource {

    private FeedbacksService feedbacksService;

    public FeedbacksResource(final FeedbacksService feedbacksService) {
        this.feedbacksService = feedbacksService;
    }

    @RequestMapping(
            value = "/feedbacks",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public Feedbacks getFeedbacks() {
        return feedbacksService.findAll();
    }

    @RequestMapping(
            value = "/feedbacks/{id}",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public Feedback getFeedback(@PathVariable final Long id) {
        return feedbacksService.findById(id);
    }

    @RequestMapping(
            value = "/feedbacks",
            method = RequestMethod.POST,
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody final FeedbackToCreate feedbackToCreate) {
        return new ResponseEntity<>(feedbacksService.save(feedbackToCreate), HttpStatus.CREATED);
    }
}
