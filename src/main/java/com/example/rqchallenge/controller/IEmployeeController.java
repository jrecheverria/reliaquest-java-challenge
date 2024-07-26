package com.example.rqchallenge.controller;

import com.example.rqchallenge.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public interface IEmployeeController {

    @GetMapping()
    ResponseEntity<List<Employee>> getAllEmployees();

    @GetMapping("/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) throws IOException;

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws IOException;

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    @PostMapping()
    ResponseEntity<String> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id) throws IOException;

}
