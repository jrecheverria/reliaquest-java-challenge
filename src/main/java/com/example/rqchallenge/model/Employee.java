package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;

@Getter
public class Employee {
    private final int id;
    private final String name;
    private final int salary;
    private final int age;
    private final String profileImage;

    @JsonCreator
    public Employee(
            @JsonProperty("id") int id,
            @JsonProperty("employee_name") String name,
            @JsonProperty("employee_salary") int salary,
            @JsonProperty("employee_age") int age,
            @JsonProperty("profile_image") String profileImage
    ) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.profileImage = profileImage;
    }
}
