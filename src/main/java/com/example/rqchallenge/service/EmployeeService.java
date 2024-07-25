package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    //Add caching
    private static final String ALL_EMPLOYEE_DATA_URL = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/employee/";
    private static final String DELETE_EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/delete/";

    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Employee> getAllEmployees() {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(ALL_EMPLOYEE_DATA_URL, EmployeeResponse.class);
        return (List<Employee>) responseEntity.getBody().getData();
    }

    public Employee getEmployeeByID(final String id) {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                EMPLOYEE_DATA_BY_ID_URL + id,
                EmployeeResponse.class
        );
        return (Employee) responseEntity.getBody().getData();
    }
}
