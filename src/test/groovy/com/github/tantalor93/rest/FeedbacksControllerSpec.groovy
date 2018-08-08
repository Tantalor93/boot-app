package com.github.tantalor93.rest

import com.github.tantalor93.config.RabbitConfig
import com.github.tantalor93.dto.FeedbackResource
import com.github.tantalor93.dto.FeedbackToCreate
import com.github.tantalor93.dto.FeedbacksResource
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static org.mockito.ArgumentMatchers.*
import static org.mockito.Mockito.verify

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise //run feature methods one by one top down
class FeedbacksControllerSpec extends Specification {

    @Autowired
    private WebTestClient webClient

    @MockBean
    private RabbitTemplate rabbitTemplate

    @Shared
    FeedbackResource resource

    def "should save feedback"() {
        when:
        resource = webClient.post()
                .uri("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromObject(
                            new FeedbackToCreate("petr", "petr@gmail.com", "is good")
                    )
                )
                .exchange()
                    .expectStatus().isCreated()
                    .expectBody(FeedbackResource).returnResult().responseBody

        then:
        verify(rabbitTemplate).convertAndSend(eq(RabbitConfig.TOPIC_EXCHANGE_NAME), matches("feedback.*"), any())

        resource.feedback.id != null
        resource.feedback.name == "petr"
        resource.feedback.email == "petr@gmail.com"
        resource.feedback.feedback == "is good"
    }

    def "should be able to find feedback by id"() {
        when:
        def result =
                webClient.get()
                .uri("/feedbacks/{id}",resource.feedback.id)
                .exchange()
                    .expectStatus().isOk()
                    .expectBody(FeedbackResource).returnResult().responseBody

        then:
        result.feedback.id == resource.feedback.id
        result.feedback.name == resource.feedback.name
        result.feedback.email == resource.feedback.email
        result.feedback.feedback == resource.feedback.feedback

    }

    def "should be able to find feedback in feedbacks"() {
        when:
        def result = webClient.get()
                .uri("/feedbacks")
                .exchange()
                    .expectStatus().isOk()
                    .expectBody(FeedbacksResource).returnResult().responseBody

        then:
        result != null
        result.feedbacks.size() == 1
        result.feedbacks[0].feedback.id == resource.feedback.id
        result.feedbacks[0].feedback.name == resource.feedback.name
        result.feedbacks[0].feedback.email == resource.feedback.email
        result.feedbacks[0].feedback.feedback == resource.feedback.feedback
        !result.feedbacks[0].links.isEmpty()
    }
}
