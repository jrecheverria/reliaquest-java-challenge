package com.example.rqchallenge.service;

import com.example.rqchallenge.controller.EmployeeController;
import com.example.rqchallenge.model.Employee;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    //Inverted Index for quick text search based on employee name tokens
    private final Map<String, Set<Employee>> invertedIndex = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.forEach(this::addEmployee);
    }

    public Integer getHighestSalary() {
        if (!employeeTreeSet.isEmpty()) {
            return Integer.parseInt(employeeTreeSet.first().getSalary());
        }
        logger.info("No employee data found while executing getHighestSalary(), returning 0");
        return 0;
    }

    // I changed this method to still retrieve the highest earning employees, even if there isn't 10 data points to fetch
    public List<String> getTopTenHighestEarningEmployeeNames() {
        if (!employeeTreeSet.isEmpty()) {
            List<String> topEmployees = new ArrayList<>();
            Iterator<Employee> iterator = employeeTreeSet.iterator();

            int count = 0;
            while (iterator.hasNext() && count < 10) {
                topEmployees.add(iterator.next().getName());
                count++;
            }
            return topEmployees;
        }
        logger.info("No employee data found while executing getTopTenHighestEarningEmployeeNames(), returning empty list");
        return new ArrayList<>();
    }

    public List<Employee> getEmployeesByNameSearch(String searchString) {
        searchString = searchString.toLowerCase();
        Set<Employee> result = new HashSet<>();

        if (invertedIndex.containsKey(searchString)) {
            result.addAll(invertedIndex.get(searchString));
            return new ArrayList<>(result);
        }
        logger.info("No employees name matched whole executing getEmployeesByNameSearch(), returning empty list");
        return new ArrayList<>();
    }

    //Tokenization method to break down an employees name into tokens
    public List<String> tokenizeEmployeeName(final String name) {
        List<String> tokens = new ArrayList<>();
        //Not efficient, but most straight forward way I can think of making a complete tokenizing system
        for (int i = 0; i < name.length(); i++) {
            for (int j = i + 1; j <= name.length(); j++) {
                tokens.add(name.substring(i, j));
            }
        }
        return tokens;
    }

    //Adds a new employee to our data structures
    public void addEmployee(Employee employee) {
        employeeMap.put(employee.getId(), employee);
        employeeTreeSet.add(employee);

        //Tokenize the employee name and add to the inverted index
        String name = employee.getName().toLowerCase();
        for (String token : tokenizeEmployeeName(name)) {
            invertedIndex.computeIfAbsent(token, k -> new HashSet<>()).add(employee);
        }
    }

    //Removes an employee from our data structures
    public void removeEmployee(final String id) {
        if (employeeMap.containsKey(id)) {
            Employee employee = employeeMap.get(id);
            employeeMap.remove(id);
            employeeTreeSet.remove(employee);

            // Remove relevant tokens from inverted index
            for (String token : tokenizeEmployeeName(employee.getName())) {
                invertedIndex.remove(token);
            }
        }
    }
}
