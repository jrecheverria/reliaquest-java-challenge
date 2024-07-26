package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/")
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        return null;
    }

    @Override
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable final String id) {
        return new ResponseEntity<>(employeeService.getEmployeeByID(id), HttpStatus.OK);
    }

    @GetMapping("/employees/top-salary")
    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        try {
            Integer salary = employeeService.getHighestSalary();
            return ResponseEntity.ok(salary);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/employees/top-ten-names-by-salary")
    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        try {
            List<String> topTenHighestEarningEmployeeNames = employeeService.getTopTenHighestEarningEmployeeNames();
            return new ResponseEntity<>(topTenHighestEarningEmployeeNames, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/employees/create")
    @Override
    public ResponseEntity<Employee> createEmployee(final Map<String, Object> employeeInput) {
        return null;
//        return new ResponseEntity<>(employeeService.createEmployee(employeeInput), HttpStatus.CREATED);
    }

    @DeleteMapping("/employees/{id}")
    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable final String id) {
        return null;
//        return new ResponseEntity<>(employeeService.deleteEmployeeByID(id), HttpStatus.OK);
    }
}
