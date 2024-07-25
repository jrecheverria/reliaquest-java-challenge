package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeResponse {
    @JsonProperty("status")
    private String status;
    @JsonProperty("data")
    public Employee employee;
}
