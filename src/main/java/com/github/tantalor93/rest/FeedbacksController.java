package com.github.tantalor93.rest;

import com.github.tantalor93.dto.Feedback;
import com.github.tantalor93.dto.FeedbackResource;
import com.github.tantalor93.dto.FeedbackToCreate;
import com.github.tantalor93.dto.FeedbacksResource;
import com.github.tantalor93.service.FeedbacksService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;


/**
 * Resource for {@link Feedback}
 */
@RestController
public class FeedbacksController {

    private FeedbacksService feedbacksService;

    public FeedbacksController(final FeedbacksService feedbacksService) {
        this.feedbacksService = feedbacksService;
    }

    @RequestMapping(
            value = "/feedbacks",
            method = RequestMethod.GET
    )
    public FeedbacksResource getFeedbacks(final Pageable pageable) {
        FeedbacksResource all = feedbacksService.findAll(pageable);
        return all;
    }

    @RequestMapping(
            value = "/feedbacks/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<FeedbackResource> getFeedback(@PathVariable final Long id) {
        return ResponseEntity.ok(feedbacksService.findById(id));
    }

    @RequestMapping(
            value = "/feedbacks",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FeedbackResource> createFeedback(@Valid @RequestBody final FeedbackToCreate feedbackToCreate) {
        final FeedbackResource saved = feedbacksService.save(feedbackToCreate);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(saved.getFeedback().getId())
                        .toUri()
        ).body(saved);
    }
}
