package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
public class Employee {
    private int id;
    @JsonProperty("employee_name")
    private String name;
    @JsonProperty("employee_salary")
    private int salary;
    @JsonProperty("employee_age")
    private int age;
    @JsonProperty("profile_image")
    private String profileImage;
}
