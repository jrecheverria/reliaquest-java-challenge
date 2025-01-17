package com.example.rqchallenge.service;

import com.example.rqchallenge.model.DeleteEmployeeResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
    @Value("${employee.api.getAll.url}")
    private String getAllEmployeeDataUrl;

    @Value("${employee.api.getById.url}")
    private String getEmployeeDataByIdUrl;

    @Value("${employee.api.deleteById.url}")
    private String deleteEmployeeDataByIdUrl;

    @Value("${employee.api.create.url}")
    private String createEmployeeDataUrl;

    private final RestTemplate restTemplate;
    private final EmployeeManager employeeManager;

    public EmployeeService(RestTemplate restTemplate, EmployeeManager employeeManager) {
        this.restTemplate = restTemplate;
        this.employeeManager = employeeManager;
    }

    public List<Employee> getAllEmployees() throws RestClientException {
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                getAllEmployeeDataUrl,
                EmployeeResponse.class);

        List<Employee> employeeList = responseEntity.getBody().getData();

        // When fetching all employees for the first time, I will load them into memory for "caching"
        if(employeeManager.getEmployeeTreeSet().isEmpty()) {
            employeeManager.populateEmployeeData(employeeList);
        }
        return employeeList;
    }

    public List<Employee> getEmployeesByNameSearch(final String searchString) {
        //If a user tries to fetch the highest salary without having the employee data populated, we will perform a 'prefetch'
        if(employeeManager.getEmployeeMap().isEmpty()) { getAllEmployees(); }

        return employeeManager.getEmployeesByNameSearch(searchString);
    }

    public Employee getEmployeeByID(final String id) throws RestClientException {
        // If employee already exists in our system, we can return the value from our "cache"
        if(employeeManager.getEmployeeMap().containsKey(id)) {
            return employeeManager.getEmployeeMap().get(id);
        }

        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.getForEntity(
                getEmployeeDataByIdUrl + id,
                EmployeeResponse.class
        );

        Employee employee = responseEntity.getBody().getData().get(0);
        employeeManager.getEmployeeMap().put(id, employee);

        return employee;
    }

    public Integer getHighestSalary() {
        //If a user tries to fetch the highest salary without having the employee data populated, we will perform a 'prefetch'
        if(employeeManager.getEmployeeMap().isEmpty()) { getAllEmployees(); }

        return employeeManager.getHighestSalary();
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        //If a user tries to fetch the highest salary without having the employee data populated, we will perform a 'prefetch'
        if(employeeManager.getEmployeeMap().isEmpty()) { getAllEmployees(); }

        return employeeManager.getTopTenHighestEarningEmployeeNames();
    }

    public String createEmployee(final Map<String, Object> employeeInput) throws RestClientException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(employeeInput, headers);
        ResponseEntity<EmployeeResponse> responseEntity = restTemplate.exchange(
                createEmployeeDataUrl,
                HttpMethod.POST,
                requestEntity,
                EmployeeResponse.class
        );
        String successStatus = responseEntity.getBody().getStatus();

        //We will add the new employee to our cache
        Employee employee = responseEntity.getBody().getData().get(0);
        employeeManager.addEmployee(employee);

        return successStatus;
    }

    /* Note, there is a mismatch between the employees in the dummy rest api, and those it returns
    in their Get All Employees API. As a result, I will still send the DELETE HTTP Request to their endpoint,
    and check whether we are storing that employee ID in memory. If we happen to have it, I will eject it.
    */
    public String deleteEmployeeByID(final String id) throws RestClientException {
        ResponseEntity<DeleteEmployeeResponse> responseEntity = restTemplate.exchange(
                deleteEmployeeDataByIdUrl + id,
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponse.class
        );

        //If the employee happens to exist in our "cache", well remove it
        employeeManager.removeEmployee(id);
        return responseEntity.getBody().getMessage();
    }
}
