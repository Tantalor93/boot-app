package com.github.tantalor93.service;

import com.github.tantalor93.exception.FeedbackNotFound;
import com.github.tantalor93.dto.Feedback;
import com.github.tantalor93.dto.FeedbackToCreate;
import com.github.tantalor93.dto.Feedbacks;
import com.github.tantalor93.entity.FeedbackEntity;
import com.github.tantalor93.repository.FeedbacksRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Service for {@link Feedback}
 */
@Service
public class FeedbacksService {

    private final FeedbacksRepository feedbacksRepository;

    private final ModelMapper modelMapper;

    public FeedbacksService(final FeedbacksRepository feedbacksRepository,
                            final ModelMapper modelMapper) {
        this.feedbacksRepository = feedbacksRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * persist feedback in DB and return persisted instance
     *
     * @param feedback to persist
     * @return persisted {@link Feedback}
     */
    public Feedback save(final FeedbackToCreate feedback) {
        final FeedbackEntity entity = modelMapper.map(feedback, FeedbackEntity.class);
        final FeedbackEntity created = feedbacksRepository.save(entity);
        return modelMapper.map(created, Feedback.class);
    }

    /**
     * Find all stored feedbacks
     *
     * @return all feedbacks
     */
    public Feedbacks findAll() {
        final Iterable<FeedbackEntity> feedbacks = feedbacksRepository.findAll();
        final List<Feedback> list = new LinkedList<>();
        feedbacks.forEach(e -> list.add(modelMapper.map(e, Feedback.class)));
        return new Feedbacks(list);
    }

    /**
     * find Feedback by ID
     *
     * @param id of {@link Feedback}
     * @return {@link Feedback} instance with given ID
     */
    public Feedback findById(final Long id) {
        notNull(id, "id should not be null");
        Optional<FeedbackEntity> byId = feedbacksRepository.findById(id);
        final FeedbackEntity entity = byId.orElseThrow(
                () -> new FeedbackNotFound("feedback with id " + id + " not found")
        );
        return modelMapper.map(entity, Feedback.class);
    }
}
