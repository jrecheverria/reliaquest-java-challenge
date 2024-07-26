package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Value("${employee.api.getAll.url}")
    private String getAllEmployeeDataUrl;

    @Value("${employee.api.getById.url}")
    private String getEmployeeDataByIdUrl;

    @Value("${employee.api.deleteById.url}")
    private String deleteEmployeeDataByIdUrl;

    @Value("${employee.api.create.url}")
    private String createEmployeeDataUrl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmployeeManager employeeManager;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> mockEmployeeList;
    private Map<String, Employee> mockEmployeeMap;

    @BeforeEach
    public void setup() {
        mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new Employee("1", "Tiger Nixon", "320800", "61", ""));
        mockEmployeeList.add(new Employee("2", "Garrett Winters", "170750", "63", ""));
        mockEmployeeList.add(new Employee("3", "Ashton Cox", "86000", "66", ""));
        mockEmployeeList.add(new Employee("4", "Cedric Kelly", "433060", "22", ""));
        mockEmployeeList.add(new Employee("5", "Airi Satou", "162700", "33", ""));

        mockEmployeeMap = new HashMap<>();
        mockEmployeeMap.put("1", mockEmployeeList.get(0));
        mockEmployeeMap.put("2", mockEmployeeList.get(1));
        mockEmployeeMap.put("3", mockEmployeeList.get(2));
        mockEmployeeMap.put("4", mockEmployeeList.get(3));
        mockEmployeeMap.put("5", mockEmployeeList.get(4));
    }

    @Test
    public void testGetAllEmployees() {
        EmployeeResponse mockEmployeeResponse = new EmployeeResponse("success", mockEmployeeList);

        when(restTemplate.getForEntity(
                getAllEmployeeDataUrl,
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockEmployeeResponse, HttpStatus.OK));

        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(mockEmployeeResponse.getData().size(), employees.size());
    }

    @Test
    public void testGetEmployeeByID() {
        Employee mockEmployee = mockEmployeeList.get(0);
        EmployeeResponse mockEmployeeResponse = new EmployeeResponse("success", List.of((mockEmployee)));

        when(restTemplate.getForEntity(
                getEmployeeDataByIdUrl + mockEmployee.getId(),
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockEmployeeResponse, HttpStatus.OK));

        Employee employee = employeeService.getEmployeeByID("1");
        assertEquals(mockEmployeeResponse.getData().size(), 1);
        assertEquals(mockEmployeeResponse.getData().get(0).getId(), mockEmployee.getId());
    }

    @Test
    public void testGetEmployeesByNameSearch() {
        // We are replicating the result of the search string "on", which should be employees Ashton Cox and Tiger Nixon
        List<Employee> mockEmployeeListBySearchString = List.of(
                mockEmployeeList.get(0),
                mockEmployeeList.get(2)
        );

        when(employeeManager.getEmployeesByNameSearch("on")).thenReturn(mockEmployeeListBySearchString);
        // Mock the call to getAllEmployees
        when(restTemplate.getForEntity(
                getAllEmployeeDataUrl,
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(new EmployeeResponse("success", mockEmployeeList), HttpStatus.OK));

        List<Employee> employees =  employeeService.getEmployeesByNameSearch("on");

        assertEquals(mockEmployeeListBySearchString, employees);
    }

    @Test
    public void testGetHighestSalary() {
        when(employeeManager.getEmployeeMap()).thenReturn(mockEmployeeMap);
        when(employeeManager.getHighestSalary()).thenReturn(433060); //Highest salary in test data belongs to Cedric

        // Mock the call to getAllEmployees
        when(restTemplate.getForEntity(
                getAllEmployeeDataUrl,
                EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(new EmployeeResponse("success", mockEmployeeList), HttpStatus.OK));

        employeeService.getAllEmployees();

        Integer highestSalary = employeeService.getHighestSalary();
        assertEquals(433060, highestSalary);
        verify(employeeManager).getHighestSalary();
    }
}
