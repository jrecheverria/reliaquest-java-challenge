package com.example.rqchallenge.service;

import com.example.rqchallenge.model.DeleteEmployeeResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmployeeService {
    private static final String ALL_EMPLOYEE_DATA_URL = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/employee/";
    private static final String DELETE_EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/delete/";
    private static final String CREATE_EMPLOYEE_DATA_URL  = "https://dummy.restapiexample.com/api/v1/create";

    private final RestTemplate restTemplate;
    private final EmployeeManager employeeManager;



    public EmployeeService(RestTemplate restTemplate, EmployeeManager employeeManager) {
        this.restTemplate = restTemplate;
        this.employeeManager = employeeManager;
    }

    public List<Employee> getAllEmployees() {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                ALL_EMPLOYEE_DATA_URL,
                EmployeeResponse.class);

        List<Employee> employeeList = responseEntity.getBody().getData();

        if(employeeManager.getEmployeePriorityQueue().isEmpty()) {
            employeeManager.populateEmployeeData(employeeList);
        }
        return employeeList;
    }

    public Employee getEmployeeByID(final String id) {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                EMPLOYEE_DATA_BY_ID_URL + id,
                EmployeeResponse.class
        );
        List<Employee> data = responseEntity.getBody().getData();
        return data.get(0);
    }

    public Integer getHighestSalary() {
        return employeeManager.getHighestSalary();
    }
}
