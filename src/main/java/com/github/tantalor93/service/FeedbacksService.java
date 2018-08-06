package com.github.tantalor93.service;

import com.github.tantalor93.dto.Feedback;
import com.github.tantalor93.dto.FeedbackResource;
import com.github.tantalor93.dto.FeedbackToCreate;
import com.github.tantalor93.dto.FeedbacksResource;
import com.github.tantalor93.entity.FeedbackEntity;
import com.github.tantalor93.exception.FeedbackNotFound;
import com.github.tantalor93.repository.FeedbacksRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Service for {@link Feedback}
 */
@Service
@Transactional
public class FeedbacksService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbacksService.class);

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
     * @return instance of {@link FeedbackResource} with persisted {@link Feedback}
     */
    public FeedbackResource save(final FeedbackToCreate feedback) {
        logger.info("action=save_feedback status=start feedback={}", feedback);
        final FeedbackEntity entity = modelMapper.map(feedback, FeedbackEntity.class);
        final FeedbackEntity created = feedbacksRepository.save(entity);
        final FeedbackResource feedbackResource = new FeedbackResource(modelMapper.map(created, Feedback.class));
        logger.info("action=save_feedback status=finished id={}", created.getId());
        return feedbackResource;
    }

    /**
     * Find all stored feedbacks
     *
     * @return all feedbacks
     */
    public FeedbacksResource findAll() {
        logger.info("action=find_all_feedbacks status=start");
        final Iterable<FeedbackEntity> feedbacks = feedbacksRepository.findAll();
        final List<FeedbackResource> list = new LinkedList<>();
        feedbacks.forEach(e -> list.add(new FeedbackResource(modelMapper.map(e, Feedback.class))));
        final FeedbacksResource feedbacksResource = new FeedbacksResource(list);
        logger.info("action=find_all_feedbacks status=finished found={}", list.size());
        return feedbacksResource;
    }

    /**
     * find Feedback by ID
     *
     * @param id of {@link Feedback}
     * @return {@link FeedbackResource} instance with given ID
     */
    public FeedbackResource findById(final Long id) {
        logger.info("action=find_feedback_by_id status=start id={}", id);
        notNull(id, "id should not be null");
        final Optional<FeedbackEntity> byId = feedbacksRepository.findById(id);
        final FeedbackEntity entity = byId.orElseThrow(
                () -> {
                    logger.info("action=find_feedback_by_id found=true status=finished ", id);
                    return new FeedbackNotFound("feedback with id " + id + " not found");
                }
        );
        final FeedbackResource feedbackResource = new FeedbackResource(modelMapper.map(entity, Feedback.class));
        logger.info("action=find_feedback_by_id found=true status=finished ", id);

        return feedbackResource;
    }
}
