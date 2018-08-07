package com.github.tantalor93.repository;

import com.github.tantalor93.entity.FeedbackEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for {@link FeedbackEntity}
 */
public interface FeedbacksRepository extends PagingAndSortingRepository<FeedbackEntity, Long> {
}
