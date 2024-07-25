package com.example.rqchallenge.model;

import com.example.rqchallenge.util.EmployeeDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonDeserialize(using = EmployeeDeserializer.class)
public class Employee {
    private final String id;
    private final String name;
    private final String salary;
    private final String age;
    private final String profileImage;

    @JsonCreator
    public Employee(
            @JsonProperty("id") String id,
            @JsonProperty("employee_name") String name,
            @JsonProperty("employee_salary") String salary,
            @JsonProperty("employee_age") String age,
            @JsonProperty("profile_image") String profileImage
    ) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.profileImage = profileImage;
    }
}
