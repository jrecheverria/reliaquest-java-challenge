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

        assertEquals(4, result.size());
        assertEquals("Tyrion Lannister", result.get(0).getName());
        assertEquals("Ned Stark", result.get(1).getName());
        assertEquals("Rob Stark", result.get(2).getName());
        assertEquals("Arya Stark", result.get(3).getName());
    }

}
