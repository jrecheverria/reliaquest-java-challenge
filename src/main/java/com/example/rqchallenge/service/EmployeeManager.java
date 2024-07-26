package com.example.rqchallenge.service;

import com.example.rqchallenge.model.Employee;
import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
@Setter
public class EmployeeManager {

    //HashMap for quick lookup based on employee ID
    private final Map<String, Employee> employeeMap = new HashMap<>();

    //TreeSet for constant order and quick look up of employees based on salary
    private final TreeSet<Employee> employeeTreeSet = new TreeSet<>(
                (e1, e2) -> Integer.compare(Integer.parseInt(e2.getSalary()), Integer.parseInt(e1.getSalary())));

    //HashMap for quick lookup and construction of Trie for text search
    private final Map<String, Employee> employeeNameMap = new HashMap<>();

    //AhoCorasickDoubleArrayTrie for lightning fast text search
    private final AhoCorasickDoubleArrayTrie<Employee> acdat = new AhoCorasickDoubleArrayTrie<>();


    public void populateEmployeeData(List<Employee> employeeList) {
        employeeList.forEach(employee -> {
            employeeMap.put(employee.getId(), employee);
            employeeTreeSet.add(employee);
            employeeNameMap.put(employee.getName(), employee);
        });
        acdat.build(employeeNameMap);
    }

    public void rebuildTrie() {
        acdat.build(employeeNameMap);
    }

    public Integer getHighestSalary() {
        if (!employeeTreeSet.isEmpty()) {
            return Integer.parseInt(employeeTreeSet.first().getSalary());
        }
        throw new NoSuchElementException("No employees found in the data set.");
    }

    public List<String> getTopTenHighestEarningEmployeeNames() {
        if (employeeTreeSet.size() >= 10) {
            List<String> topEmployees = new ArrayList<>();
            Iterator<Employee> iterator = employeeTreeSet.iterator();

            int count = 0;
            while (iterator.hasNext() && count < 10) {
                topEmployees.add(iterator.next().getName());
                count++;
            }
            return topEmployees;
        }
        throw new NoSuchElementException("Not enough employees found in data to retrieve top 10 salaries.");
    }

    public List<Employee> getEmployeesByNameSearch(final String searchString) {
        List<AhoCorasickDoubleArrayTrie.Hit<Employee>> wordList = acdat.parseText(searchString);
        return null;
    }
}
