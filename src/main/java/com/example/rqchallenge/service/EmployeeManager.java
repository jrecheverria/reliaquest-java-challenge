package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@Setter
public class EmployeeManager {

    //Two data structures to act as a sort of caching mechanism to save time on our API call
    //HashMap for quick lookup based on employee ID
    private final Map<String, Employee> employeeMap = new HashMap<>();
    //TreeSet for constant order and quick look up of employees based on salary
    private final TreeSet<Employee> employeeTreeSet = new TreeSet<>(
                (e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));

    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.forEach(employee -> {
            employeeMap.put(employee.getId(), employee);
            employeeTreeSet.add(employee);
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
