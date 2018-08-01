package com.github.tantalor93.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Feedback not found
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FeedbackNotFound extends RuntimeException {
    public FeedbackNotFound(String message) {
        super(message);
    }
}
