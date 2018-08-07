package com.github.tantalor93.service

import com.github.tantalor93.config.ApplicationConfig
import com.github.tantalor93.dto.FeedbackToCreate
import com.github.tantalor93.dto.FeedbacksResource
import com.github.tantalor93.entity.FeedbackEntity
import com.github.tantalor93.exception.FeedbackNotFound
import com.github.tantalor93.repository.FeedbacksRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject

@ContextConfiguration
@Import(ApplicationConfig)
class FeedbacksServiceSpec extends Specification {

    def feedbacksRepository = Mock(FeedbacksRepository)

    @Autowired
    ModelMapper modelMapper

    @Subject
    FeedbacksService feedbacksService

    def setup() {
        feedbacksService = new FeedbacksService(feedbacksRepository, modelMapper)
    }


    def "should save feedback"() {
        given:
        def name = "petr"
        def email = "ano@gmail.com"
        def feedback = "is good"
        def toCreate = new FeedbackToCreate(name, email, feedback)

        and:
        1 * feedbacksRepository.save({(it as FeedbackEntity).name == name}) >> new FeedbackEntity(1L, name, email, feedback)

        when:
        def saved = feedbacksService.save(toCreate)

        then:
        saved?.feedback?.name == name
        saved?.feedback?.email == email
        saved?.feedback?.feedback == feedback
    }

    def "should find all feedbacks"() {
        given:
        def list = new PageImpl([
                new FeedbackEntity(1, "petr", "petr@gmail.com", "it is good"),
                new FeedbackEntity(2, "josef", "josef@gmail.com", "it is bad")
        ])

        and:
        1 * feedbacksRepository.findAll(_ as Pageable) >> list

        when:
        FeedbacksResource result = feedbacksService.findAll(PageRequest.of(0, 10))

        then:
        result?.feedbacks?.size() == 2

        result?.totalElements == 2
        result?.totalPages == 1

        result?.feedbacks[0].feedback.id == 1L
        result?.feedbacks[0].feedback.name == "petr"
        result?.feedbacks[0].feedback.email == "petr@gmail.com"
        result?.feedbacks[0].feedback.feedback == "it is good"

        result?.feedbacks[1].feedback.id == 2L
        result?.feedbacks[1].feedback.name == "josef"
        result?.feedbacks[1].feedback.email == "josef@gmail.com"
        result?.feedbacks[1].feedback.feedback == "it is bad"
    }

    def "should find feedback by id"() {
        given:
        feedbacksRepository.findById(1L) >> Optional.of(
                new FeedbackEntity(1L, "petr", "petr@gmail.com", "it is good")
        )

        when:
        def result = feedbacksService.findById(1L)

        then:
        result?.feedback?.id == 1L
        result?.feedback?.name == "petr"
        result?.feedback?.email == "petr@gmail.com"
        result?.feedback?.feedback == "it is good"
    }

    def "should not find feedback by id"() {
        given:
        feedbacksRepository.findById(1L) >> Optional.empty()

        when:
        feedbacksService.findById(1L)

        then:
        thrown(FeedbackNotFound)
    }
}
