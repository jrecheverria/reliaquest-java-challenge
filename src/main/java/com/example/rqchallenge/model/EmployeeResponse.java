package com.example.rqchallenge.model;

import com.example.rqchallenge.util.EmployeeDataDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

@Getter
public class EmployeeResponse {
    private final String status;
    private final Object data;
    private final String message;

    @JsonCreator
    public EmployeeResponse(
            @JsonProperty("status") String status,
            @JsonProperty("data") Object data,
            @JsonProperty("message") String message
    ) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
