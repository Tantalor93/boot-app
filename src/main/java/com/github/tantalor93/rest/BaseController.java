package com.github.tantalor93.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BaseController {

    @RequestMapping("/")
    public ResponseEntity<ResourceSupport> index() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(linkTo(methodOn(FeedbacksController.class).getFeedbacks(Pageable.unpaged())).withRel("feedbacks"));
        return ResponseEntity.ok(resource);
    }

}
