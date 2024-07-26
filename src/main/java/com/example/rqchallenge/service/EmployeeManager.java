package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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

    public Integer getHighestSalary() {
        if (!employeePriorityQueue.isEmpty()) {
            return Integer.parseInt(employeePriorityQueue.peek().getSalary());
        }
        throw new NoSuchElementException("No employees found in the priority queue.");
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        if (employeePriorityQueue.size() >= 10) {
            List<String> topTenEaringEmployees = new ArrayList<>();
            int count = 0;
            while (!employeePriorityQueue.isEmpty() && count < 10) {
                Employee employee = employeePriorityQueue.poll();
                topTenEaringEmployees.add(employee.getName());
                employeePriorityQueue.offer(employee);
                count++;
            }
            return topTenEaringEmployees;
        }
        throw new NoSuchElementException("Not enough employees found in data to retrieve top 10 salaries.");
    }

}
