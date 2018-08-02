package com.github.tantalor93.service

import com.github.tantalor93.config.ApplicationConfig
import com.github.tantalor93.dto.Feedback
import com.github.tantalor93.dto.FeedbackToCreate
import com.github.tantalor93.dto.Feedbacks
import com.github.tantalor93.entity.FeedbackEntity
import com.github.tantalor93.exception.FeedbackNotFound
import com.github.tantalor93.repository.FeedbacksRepository
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
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
        saved.name == name
        saved.email == email
        saved.feedback == feedback
    }

    def "should find all feedbacks"() {
        given:
        def list = [
                new FeedbackEntity(1, "petr", "petr@gmail.com", "it is good"),
                new FeedbackEntity(2, "josef", "josef@gmail.com", "it is bad")
        ]

        and:
        1 * feedbacksRepository.findAll() >> list

        when:
        Feedbacks result = feedbacksService.findAll()

        then:
        result?.feedbacks?.size() == 2
        result?.feedbacks[0].id == 1L
        result?.feedbacks[0].name == "petr"
        result?.feedbacks[0].email == "petr@gmail.com"
        result?.feedbacks[0].feedback == "it is good"
        result?.feedbacks[1].id == 2L
        result?.feedbacks[1].name == "josef"
        result?.feedbacks[1].email == "josef@gmail.com"
        result?.feedbacks[1].feedback == "it is bad"
    }

    def "should find feedback by id"() {
        given:
        feedbacksRepository.findById(1L) >> Optional.of(
                new FeedbackEntity(1L, "petr", "petr@gmail.com", "it is good")
        )

        expect:
        feedbacksService.findById(1L) == new Feedback(1L, "petr", "petr@gmail.com", "it is good")
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
