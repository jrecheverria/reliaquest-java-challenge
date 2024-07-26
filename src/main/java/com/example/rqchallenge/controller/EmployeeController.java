package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @GetMapping()
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        ResponseEntity<List<Employee>> employees = new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
        logger.info("Successfully retrieved all employees");
        return employees;
    }

    @GetMapping("/search/{searchString}")
    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) throws IOException {
        ResponseEntity<List<Employee>> employees =  new ResponseEntity<>(employeeService.getEmployeesByNameSearch(searchString), HttpStatus.OK);
        logger.info("Successfully retrieved all employees by search string {}", searchString);
        return employees;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable final String id) throws IOException {
        ResponseEntity<Employee> employeeById = new ResponseEntity<>(employeeService.getEmployeeByID(id), HttpStatus.OK);
        logger.info("Successfully retrieved employee with id {}", id);
        return employeeById;
    }

    @GetMapping("/highestSalary")
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        try {
            Integer salary = employeeService.getHighestSalary();
            logger.info("Successfully fetched highest earnings of salary.");
            return ResponseEntity.ok(salary);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/topTenHighestEarningEmployeeNames")
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        try {
            List<String> topTenHighestEarningEmployeeNames = employeeService.getTopTenHighestEarningEmployeeNames();
            logger.info("Successfully fetched top ten earning employee names.");
            return new ResponseEntity<>(topTenHighestEarningEmployeeNames, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping()
    @Override
    public ResponseEntity<String> createEmployee(final Map<String, Object> employeeInput) {
        ResponseEntity<String> createdEmployee = new ResponseEntity<>(employeeService.createEmployee(employeeInput), HttpStatus.CREATED);
        logger.info("Successfully created new employee.");
        return createdEmployee;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable final String id) throws IOException {
        ResponseEntity<String> deletedEmployee = new ResponseEntity<>(employeeService.deleteEmployeeByID(id), HttpStatus.OK);
        logger.info("Successfully deleted employee with id {}.", id);
        return deletedEmployee;
    }
}
