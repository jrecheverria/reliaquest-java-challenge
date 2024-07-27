package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeleteEmployeeResponse {
    private final String status;
    private final String message;

    public DeleteEmployeeResponse(
            @JsonProperty("status") String status,
            @JsonProperty("message") String message) {
        this.status = status;
        this.message = message;
    }
}
