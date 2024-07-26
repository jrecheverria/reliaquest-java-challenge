package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagerTest {

    private EmployeeManager employeeManager;
    private List<Employee> employeeList;

    @BeforeEach
    void setUp() {
        employeeManager = new EmployeeManager();
        employeeList = Arrays.asList(
                new Employee("1", "Jon Snow", "100000", "28", "CastleBlack.jpg"),
                new Employee("2", "Arya Stark", "120000", "18", "Winterfell.jpg"),
                new Employee("3", "Tyrion Lannister", "110000", "40", "CasterlyRock.jpg"),
                new Employee("4", "Daenerys Targaryen", "130000", "25", "Dragonstone.jpg"),
                new Employee("5", "Rob Stark", "100000", "30", "Winterfell.jpg"),
                new Employee("6", "Ned Stark", "100000", "50", "Winterfell.jpg")
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

    @Test
    void testGetEmployeesByNameSearch() {
        //Using search string 'st' should fetch some Lannisters and Starks
        List<Employee> result = employeeManager.getEmployeesByNameSearch("St");
        Set<String> employeeNames = result.stream().map(Employee::getName).collect(Collectors.toSet());

        assertEquals(4, result.size());
        assertTrue(employeeNames.contains("Rob Stark"));
        assertTrue(employeeNames.contains("Ned Stark"));
        assertTrue(employeeNames.contains("Arya Stark"));
        assertTrue(employeeNames.contains("Tyrion Lannister"));
    }

    @Test
    void testGetEmployeesByName_No_Matches() {
        List<Employee> result = employeeManager.getEmployeesByNameSearch("Sansa");
        assertEquals(0, result.size());
    }

    @Test
    void testTokenizeEmployeeName() {
        String name = "Jon";
        List<String> tokens = employeeManager.tokenizeEmployeeName(name);
        List<String> expectedTokens = Arrays.asList(
                "J", "Jo", "Jon", "o", "on", "n"
        );
        assertEquals(expectedTokens, tokens);
    }

    @Test
    void testAddEmployee() {
        Employee newEmployee = new Employee("7", "Sansa Stark", "90000", "24", "Winterfell.jpg");
        employeeManager.addEmployee(newEmployee);

        assertTrue(employeeManager.getEmployeeMap().containsKey("7"));
        assertTrue(employeeManager.getEmployeeTreeSet().contains(newEmployee));

        Set<Employee> sansaSet = employeeManager.getInvertedIndex().get("sansa");
        assertNotNull(sansaSet);
        assertTrue(sansaSet.contains(newEmployee));
    }

    @Test
    void testRemoveEmployee() {
        Employee employeeToRemove = new Employee("1", "Jon Snow", "100000", "28", "CastleBlack.jpg");
        employeeManager.removeEmployee("1");

        assertFalse(employeeManager.getEmployeeMap().containsKey("1"));
        assertFalse(employeeManager.getEmployeeTreeSet().contains(employeeToRemove));
        assertFalse(employeeManager.getInvertedIndex().containsKey("jon"));
    }
}
