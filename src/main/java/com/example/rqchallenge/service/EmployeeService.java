package com.example.rqchallenge.service;

import com.example.rqchallenge.model.DeleteEmployeeResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    private static final String GET_ALL_EMPLOYEE_DATA_URL = "https://dummy.restapiexample.com/api/v1/employees";
    private static final String GET_EMPLOYEE_DATA_BY_ID_URL = "https://dummy.restapiexample.com/api/v1/employee/";
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
                GET_ALL_EMPLOYEE_DATA_URL,
                EmployeeResponse.class);

        List<Employee> employeeList = responseEntity.getBody().getData();

        // When fetching all employees for the first time, I will load them into memory for "caching"
        if(employeeManager.getEmployeeTreeSet().isEmpty()) {
            employeeManager.populateEmployeeData(employeeList);
        }
        return employeeList;
    }

    public List<Employee> getEmployeesByNameSearch(final String searchString) {
        return employeeManager.getEmployeesByNameSearch(searchString);
    }

    public Employee getEmployeeByID(final String id) {
        // If employee already exists in our system, we can return the value from our "cache"
        if(employeeManager.getEmployeeMap().containsKey(id)) {
            return employeeManager.getEmployeeMap().get(id);
        }

        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                GET_EMPLOYEE_DATA_BY_ID_URL + id,
                EmployeeResponse.class
        );

        Employee employee = responseEntity.getBody().getData().get(0);
        employeeManager.getEmployeeMap().put(id, employee);

        return employee;
    }

    public Integer getHighestSalary() {
        return employeeManager.getHighestSalary();
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        return employeeManager.getTopTenHighestEarningEmployeeNames();
    }

    public String createEmployee(final Map<String, Object> employeeInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(employeeInput, headers);
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.exchange(
                CREATE_EMPLOYEE_DATA_URL,
                HttpMethod.POST,
                requestEntity,
                EmployeeResponse.class
        );
        String successStatus = responseEntity.getBody().getStatus();
        Employee employee = responseEntity.getBody().getData().get(0);

        //Adding the new employee to our data structures
        employeeManager.getEmployeeMap().put(employee.getId(), employee);
        employeeManager.getEmployeeTreeSet().add(employee);
        employeeManager.getEmployeeNameMap().put(employee.getName(), employee);
        employeeManager.rebuildTrie();

        return successStatus;
    }

    /* Note, there is a mismatch between the employees in the dummy rest api, and those it returns
    in their Get All Employees API. As a result, I will still send the DELETE HTTP Request to their endpoint,
    and check whether we are storing that employee ID in memory. If we happen to have it, I will eject it.
    */
    public String deleteEmployeeByID(final String id) {
        ResponseEntity<DeleteEmployeeResponse> responseEntity = restTemplate.exchange(
                DELETE_EMPLOYEE_DATA_BY_ID_URL + id,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponse.class
        );

        //If the employee happens to exist in our "cache", well remove it
        if(employeeManager.getEmployeeMap().containsKey(id)) {
            Employee employee = employeeManager.getEmployeeMap().get(id);
            employeeManager.getEmployeeMap().remove(id);
            employeeManager.getEmployeeTreeSet().remove(employee);
            employeeManager.rebuildTrie();
        }
        return responseEntity.getBody().getMessage();
    }
}
