package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeResponse {
    private final String status;

    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private final List<Employee> data;

    @JsonCreator
    public EmployeeResponse(
            @JsonProperty("status") String status,
            @JsonProperty("data") List<Employee> data
    ) {
        this.status = status;
        this.data = data;
    }
}
