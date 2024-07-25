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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Employee> employeeMap = new HashMap<>();
    private final PriorityQueue<Employee> employeePriorityQueue = new PriorityQueue<>((e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAllEmployees() {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                ALL_EMPLOYEE_DATA_URL,
                EmployeeResponse.class);
        return responseEntity.getBody().getData();
    }

    public Employee getEmployeeByID(final String id) {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                EMPLOYEE_DATA_BY_ID_URL + id,
                EmployeeResponse.class
        );
        List<Employee> data = responseEntity.getBody().getData();
        return data.get(0);
    }
}
