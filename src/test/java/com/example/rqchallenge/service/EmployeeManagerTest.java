package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeManager = new EmployeeManager();
        employeeList = Arrays.asList(
                new Employee("1", "Jon Snow", "100000", "28", "Castle_Black.jpg"),
                new Employee("2", "Arya Stark", "120000", "18", "Winterfell.jpg"),
                new Employee("3", "Tyrion Lannister", "110000", "40", "Casterly_Rock.jpg"),
                new Employee("4", "Daenerys Targaryen", "130000", "25", "Dragonstone.jpg")
        );
        employeeManager.populateEmployeeData(employeeList);
    }

    @Test
    void testPopulateEmployeeData() {
        assertFalse(employeeManager.getInvertedIndex().isEmpty());
    }

    @Test
    void testGetHighestSalary() {
        assertEquals(employeeManager.getHighestSalary(), 130000);
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<String> topEmployees = employeeManager.getTopTenHighestEarningEmployeeNames();
        List<String> expectedNames = Arrays.asList(
                "Daenerys Targaryen", "Arya Stark", "Tyrion Lannister", "Jon Snow"
        );
        assertEquals(expectedNames, topEmployees);
    }

}
