package com.github.tantalor93.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tantalor93.dto.Feedback
import com.github.tantalor93.dto.FeedbackToCreate
import com.github.tantalor93.dto.Feedbacks
import com.github.tantalor93.service.FeedbacksService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

import static org.hamcrest.CoreMatchers.is
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.doReturn
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest
class FeedbackResourceMvcSpec extends Specification {

    private static final Feedback FEEDBACK1 = new Feedback(1, "Petr", "Petr@gmail.com", "good")
    private static final Feedback FEEDBACK2 = new Feedback(2, "Ivana", "Sito@gmail.com", "bad bad")

    @Autowired
    MockMvc mvc

    @MockBean
    FeedbacksService feedbacksService

    @Shared
    ObjectMapper objectMapper = new ObjectMapper()

    def "should get all feedbacks"() {
        given:
        doReturn(
                new Feedbacks(
                        [
                                FEEDBACK1,
                                FEEDBACK2,
                        ]
                )
        ).when(feedbacksService).findAll()

        expect:
        mvc.perform(
                MockMvcRequestBuilders.get("/feedbacks")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("\$.feedbacks[0].feedback.id", is(FEEDBACK1.id.intValue())))
                .andExpect(jsonPath("\$.feedbacks[0].feedback.name", is(FEEDBACK1.name)))
                .andExpect(jsonPath("\$.feedbacks[0].feedback.email", is(FEEDBACK1.email)))
                .andExpect(jsonPath("\$.feedbacks[0].feedback.feedback", is(FEEDBACK1.feedback)))
                .andExpect(jsonPath("\$.feedbacks[1].feedback.id", is(FEEDBACK2.id.intValue())))
                .andExpect(jsonPath("\$.feedbacks[1].feedback.name", is(FEEDBACK2.name)))
                .andExpect(jsonPath("\$.feedbacks[1].feedback.email", is(FEEDBACK2.email)))
                .andExpect(jsonPath("\$.feedbacks[1].feedback.feedback", is(FEEDBACK2.feedback)))
    }

    def "should get feedback by id"() {
        given:
        doReturn(
                FEEDBACK1
        ).when(feedbacksService).findById(eq(1L))

        expect:
        mvc.perform(
                MockMvcRequestBuilders.get("/feedbacks/1")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("\$.feedback.id", is(FEEDBACK1.id.intValue())))
                .andExpect(jsonPath("\$.feedback.name", is(FEEDBACK1.name)))
                .andExpect(jsonPath("\$.feedback.email", is(FEEDBACK1.email)))
                .andExpect(jsonPath("\$.feedback.feedback", is(FEEDBACK1.feedback)))

    }

    def "should save feedback"() {
        given:
        doReturn(
                FEEDBACK1
        ).when(feedbacksService).save(eq(new FeedbackToCreate(FEEDBACK1.name, FEEDBACK1.email, FEEDBACK1.feedback)))

        expect:
        mvc.perform(
                MockMvcRequestBuilders.post("/feedbacks")
                        .content(objectMapper.writeValueAsString(FEEDBACK1))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("\$.feedback.id", is(FEEDBACK1.id.intValue())))
                .andExpect(jsonPath("\$.feedback.name", is(FEEDBACK1.name)))
                .andExpect(jsonPath("\$.feedback.email", is(FEEDBACK1.email)))
                .andExpect(jsonPath("\$.feedback.feedback", is(FEEDBACK1.feedback)))

    }
}
