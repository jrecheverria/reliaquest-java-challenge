package com.example.rqchallenge.model;

import com.example.rqchallenge.util.EmployeeDataDeserializer;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public class EmployeeResponse {
    private String status;

    @JsonDeserialize(using = EmployeeDataDeserializer.class)
    public Object data;
}
