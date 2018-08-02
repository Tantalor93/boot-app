package com.github.tantalor93.repository

import com.github.tantalor93.entity.FeedbackEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

@DataJpaTest
class FeedbacksRepositorySpec extends Specification {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    FeedbacksRepository feedbacksRepository

    def "should find all feedbacks"() {
        given:
        entityManager.persist(new FeedbackEntity("petr", "petr@gmail.com", "it is good"))
        entityManager.persist(new FeedbackEntity("jan", "jan@gmail.com", "it is bad"))

        when:
        def it = feedbacksRepository.findAll()

        and:
        List<FeedbackEntity> list = it.findAll()

        then:
        list[0].id != null
        list[0].name == "petr"
        list[1].id != null
        list[1].name == "jan"
    }

    def "should find by id"() {
        given:
        def persist = entityManager.persist(new FeedbackEntity("petr", "petr@gmail.com", "it is good"))

        when:
        Optional<FeedbackEntity> result = feedbacksRepository.findById(persist.id)

        then:
        FeedbackEntity entity = result.get()

        and:
        entity.id != null
        entity.name == "petr"
        entity.email == "petr@gmail.com"
        entity.feedback == "it is good"
    }

    def "should not find by id"() {
        when:
        Optional<FeedbackEntity> result = feedbacksRepository.findById(1L)

        then:
        result.isPresent() == false
    }

    def "should save entity"() {
        when:
        FeedbackEntity feedbackEntity = feedbacksRepository.save(new FeedbackEntity("petr", "petr@gmail.com", "it is good"))

        then:
        def persisted = entityManager.find(FeedbackEntity, feedbackEntity.id)

        and:
        persisted.id == feedbackEntity.id
        persisted.name == "petr"
    }
}
