package com.example.rqchallenge.util;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EmployeeDataDeserializer extends JsonDeserializer<Object> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode node = objectMapper.readTree(jsonParser);
        if (node.isArray()) {
            List<Employee> employees = new ArrayList<>();
            Iterator<JsonNode> elements = node.elements();
            while (elements.hasNext()) {
                JsonNode employee = elements.next();
                employees.add(objectMapper.treeToValue(node, Employee.class));
            }
            return employees;
        } else {
            return objectMapper.treeToValue(node, Employee.class);
        }
    }
}
