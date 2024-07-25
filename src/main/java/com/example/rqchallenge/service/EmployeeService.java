package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
    //Add caching
    private static final String ALL_EMPLOYEE_DATA_URL = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/employee/";

    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Employee getEmployeeByID(final String id) {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                EMPLOYEE_DATA_BY_ID_URL + id,
                EmployeeResponse.class
        );
        EmployeeResponse employeeResponse = responseEntity.getBody();
        return (Employee) employeeResponse.getData();
    }
}
