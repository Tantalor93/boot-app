package com.github.tantalor93.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tantalor93.rest.FeedbacksController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
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

    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean last;

    public FeedbacksResource(List<FeedbackResource> feedbacks, Page page, Pageable pageable) {
        this.feedbacks = feedbacks;

        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.last = page.isLast();

        add(getLinkForPageable(pageable).withSelfRel());

        if(!last) {
            add(getLinkForPageable(pageable.next()).withRel("next"));
        }
    }

    private Link getLinkForPageable(Pageable pageable) {
        return new Link(
                linkTo(methodOn(FeedbacksController.class).getFeedbacks(pageable))
                        .toUriComponentsBuilder()
                        .query("page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize())
                        .build()
                        .toString()
        );
    }

    // needed for webflux
    protected FeedbacksResource() {
    }

    public List<FeedbackResource> getFeedbacks() {
        return Collections.unmodifiableList(feedbacks);
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public String toString() {
        return "FeedbacksResource{" +
                "feedbacks=" + feedbacks +
                '}';
    }
}
