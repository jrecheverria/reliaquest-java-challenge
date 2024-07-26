package com.example.rqchallenge.service;

import com.example.rqchallenge.model.DeleteEmployeeResponse;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    @Test
    public void testGetTopTenHighestEarningEmployeeNames() {
        List<String> topXEarningNames = mockEmployeeList.stream()
                .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())))
                .map(Employee::getName)
                .limit(10)
                .collect(Collectors.toList());

        when(employeeManager.getEmployeeMap()).thenReturn(mockEmployeeMap);
        when(employeeManager.getTopTenHighestEarningEmployeeNames()).thenReturn(topXEarningNames);

        List<String> employeeNames = employeeService.getTopTenHighestEarningEmployeeNames();

        assertEquals(topXEarningNames, employeeNames);
    }

    @Test
    public void testCreateEmployee() {
        Map<String, Object> newEmployeeData = Map.of(
                "name", "Jon Snow",
                "salary", "100000",
                "age", "28"
        );
        List<Employee> newEmployeeList = List.of(new Employee("6", "Jon Snow", "100000", "28", "Castle_Black.jpg"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(newEmployeeData, headers);

        when(restTemplate.exchange(
            createEmployeeDataUrl,
            HttpMethod.POST,
            requestEntity,
            EmployeeResponse.class
        )).thenReturn(new ResponseEntity<>(new EmployeeResponse("success", newEmployeeList), HttpStatus.OK));

        mockEmployeeMap.put("6", newEmployeeList.get(0));
        when(employeeManager.getEmployeeMap()).thenReturn(mockEmployeeMap);
        when(employeeManager.getEmployeeTreeSet()).thenReturn(new TreeSet<>(Comparator.comparing(Employee::getId)));

        assertEquals(employeeService.createEmployee(newEmployeeData), "success");
        assertTrue(mockEmployeeMap.containsKey("6"));
    }

    @Test
    public void testDeleteEmployee() {
        when(restTemplate.exchange(
                deleteEmployeeDataByIdUrl + "1",
                HttpMethod.DELETE,
                null,
                DeleteEmployeeResponse.class
        )).thenReturn(new ResponseEntity<>(new DeleteEmployeeResponse("success", "Successfully! Record has been deleted"), HttpStatus.OK));

        mockEmployeeMap.remove("1");
        when(employeeManager.getEmployeeMap()).thenReturn(mockEmployeeMap);

        assertEquals(employeeService.deleteEmployeeByID("1"), "Successfully! Record has been deleted");
        assertTrue(!mockEmployeeMap.containsKey("1"));
    }
}
