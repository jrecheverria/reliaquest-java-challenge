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

    //HashMap for quick lookup based on employee ID
    private final Map<String, Employee> employeeMap = new HashMap<>();

    //TreeSet for constant order and quick look up of employees based on salary
    private final TreeSet<Employee> employeeTreeSet = new TreeSet<>(
                (e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));

    //Inverted Index for quick text search
    private final Map<String, Set<Employee>> invertedIndex = new HashMap<>();


    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.forEach(employee -> {
            employeeMap.put(employee.getId(), employee);
            employeeTreeSet.add(employee);

            String name = employee.getName().toLowerCase();
            for (String token : tokenizeEmployeeName(name)) {
                invertedIndex.computeIfAbsent(token, k -> new HashSet<>()).add(employee);
            }
        });
    }

    public Integer getHighestSalary() {
        if (!employeeTreeSet.isEmpty()) {
            return Integer.parseInt(employeeTreeSet.first().getSalary());
        }
        throw new NoSuchElementException("No employees found in the data set.");
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

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        searchString = searchString.toLowerCase();
        Set<Employee> result = new HashSet<>();

        if (invertedIndex.containsKey(searchString)) {
            result.addAll(invertedIndex.get(searchString));
        }
        return new ArrayList<>(result);
    }

    //Tokenization method to break down an employees name
    private List<String> tokenizeEmployeeName(final String name) {
        List<String> tokens = new ArrayList<>();
        //O(n)^2
        for (int i = 0; i < name.length(); i++) {
            for (int j = i + 1; j <= name.length(); j++) {
                tokens.add(name.substring(i, j));
            }
        }
        return tokens;
    }
}
