package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable final String id) {
        return new ResponseEntity<>(employeeService.getEmployeeByID(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        return null;
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<Employee> createEmployee(final Map<String, Object> employeeInput) {
        return null;
//        return new ResponseEntity<>(employeeService.createEmployee(employeeInput), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteEmployeeById(@PathVariable final String id) {
        return null;
//        return new ResponseEntity<>(employeeService.deleteEmployeeByID(id), HttpStatus.OK);
    }
}
