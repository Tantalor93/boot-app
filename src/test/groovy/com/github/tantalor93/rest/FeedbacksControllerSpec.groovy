package com.github.tantalor93.rest

import com.github.tantalor93.dto.Feedback
import com.github.tantalor93.dto.FeedbackToCreate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Stepwise //run feature methods one by one top down
class FeedbacksControllerSpec extends Specification {

    @Autowired
    private WebTestClient webClient

    @Shared
    Feedback feed

    def "should save feedback"() {
        when:
        feed = webClient.post()
                .uri("/feedbacks")
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    BodyInserters.fromObject(
                            new FeedbackToCreate("petr", "petr@gmail.com", "is good")
                    )
                )
                .exchange()
                    .expectStatus().isCreated()
                    .expectBody(Feedback).returnResult().responseBody

        then:
        feed.id != null
        feed.name == "petr"
        feed.email == "petr@gmail.com"
        feed.feedback == "is good"
    }

    def "should be able to find feedback by id"() {
        expect:
        webClient.get()
                .uri("/feedbacks/{id}",feed.id)
                .exchange()
                    .expectStatus().isOk()
                    .expectBody(Feedback).isEqualTo(feed)
    }

    def "should be able to find feedback in feedbacks"() {
        expect:
        webClient.get()
                .uri("/feedbacks")
                .exchange()
                    .expectStatus().isOk()
    }
}
