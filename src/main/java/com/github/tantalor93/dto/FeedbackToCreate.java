package com.github.tantalor93.dto;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("feedback")
public class FeedbackToCreate {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String feedback;

    public FeedbackToCreate(String name, String email, String feedback) {
        this.name = name;
        this.email = email;
        this.feedback = feedback;
    }

    protected FeedbackToCreate() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFeedback() {
        return feedback;
    }

    @Override
    public String toString() {
        return "FeedbackToCreate{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", feedback='" + feedback + '\'' +
                '}';
    }
}
