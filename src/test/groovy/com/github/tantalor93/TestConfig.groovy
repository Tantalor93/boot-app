package com.github.tantalor93

import com.github.tantalor93.service.FeedbacksService
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

class TestConfig {
    //to be able to create spock mocks outside of spec
    private final detachedMockFactory = new DetachedMockFactory()

    @Bean
    FeedbacksService feedbacksService() {
        detachedMockFactory.Mock(FeedbacksService)
    }
}
