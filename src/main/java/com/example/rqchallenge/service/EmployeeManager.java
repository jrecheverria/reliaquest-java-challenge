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

    private final TreeSet<Employee> employeeTreeSet = new TreeSet<>(
            (e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));


    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.stream().forEach(employee -> {
            employeeTreeSet.add(employee); // Add to priority queue
        });
    }

    public Integer getHighestSalary() {
        if (!employeeTreeSet.isEmpty()) {
            return Integer.parseInt(employeeTreeSet.first().getSalary());
        }
        throw new NoSuchElementException("No employees found in the priority queue.");
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        if (employeeTreeSet.size() >= 10) {
            List<String> topEmployees = new ArrayList<>();
            Iterator<Employee> iterator = employeeTreeSet.iterator();

            int count = 0;
            while (iterator.hasNext() && count < 10) {
                topEmployees.add(iterator.next().getName());
                count++;
            }
            return topEmployees;
        }
        throw new NoSuchElementException("Not enough employees found in data to retrieve top 10 salaries.");
    }

}
