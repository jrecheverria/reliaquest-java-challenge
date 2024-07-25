package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Getter
@Setter
public class EmployeeManager {

    private final PriorityQueue<Employee> employeePriorityQueue = new PriorityQueue<>(
            (e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));


    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.stream().forEach(employee -> {
            employeePriorityQueue.add(employee); // Add to priority queue
        });
    }

}
