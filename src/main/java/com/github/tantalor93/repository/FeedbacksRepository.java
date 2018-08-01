package com.github.tantalor93.repository;

import com.github.tantalor93.entity.FeedbackEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for {@link FeedbackEntity}
 */
public interface FeedbacksRepository extends CrudRepository<FeedbackEntity, Long> {
}
