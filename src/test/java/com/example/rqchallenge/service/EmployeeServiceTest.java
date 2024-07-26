package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {


    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployees() {
        List<Employee> mockEmployees = List.of(
                new Employee("1", "Tiger Nixon", "320800", "61", ""),
                new Employee("2", "Garrett Winters", "170750", "63", ""),
                new Employee("3", "Ashton Cox", "86000", "66", ""),
                new Employee("4", "Cedric Kelly", "433060", "22", ""),
                new Employee("5", "Airi Satou", "162700", "33", "")
        );

        EmployeeResponse mockEmployeeResponse = new EmployeeResponse("success", mockEmployees);

        when(restTemplate.getForEntity(
                ALL_EMPLOYEE_DATA_URL,
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockEmployeeResponse, HttpStatus.OK));

        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(mockEmployees.size(), employees.size());
        assertEquals(mockEmployees, employees);
    }

    @Test
    public void testGetEmployeeByID() {
        Employee mockEmployee = new Employee("1", "Tiger Nixon", "320800", "61", "");

        EmployeeResponse mockEmployeeResponse = new EmployeeResponse("success", Arrays.asList(mockEmployee));

        when(restTemplate.getForEntity(
                EMPLOYEE_DATA_BY_ID_URL + mockEmployee.getId(),
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockEmployeeResponse, HttpStatus.OK));

        Employee employee = employeeService.getEmployeeByID("1");
        assertEquals(mockEmployee, employee);
    }
}
