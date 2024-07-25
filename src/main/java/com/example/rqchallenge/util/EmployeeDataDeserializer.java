package com.example.rqchallenge.util;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
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
        objectMapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode rootNode = objectMapper.readTree(jsonParser);

        if (rootNode.has("data") && rootNode.get("data").isArray()) {
            List<Employee> employees = objectMapper.convertValue(rootNode.get("data"), new TypeReference<List<Employee>>(){});
            return employees;
        } else {
            return objectMapper.treeToValue(rootNode, Employee.class);
        }
    }
}
