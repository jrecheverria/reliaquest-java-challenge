package com.example.rqchallenge.util;

import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class EmployeeDeserializer extends JsonDeserializer<Employee> {

    @Override
    public Employee deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        String id = getJsonNodeText(rootNode, "id");
        String name = fetchJsonNodeAlias(rootNode, "employee_name", "name");
        String salary = getJsonNodeText(rootNode, "employee_salary");
        String age = getJsonNodeText(rootNode, "employee_age");
        String profileImage = getJsonNodeText(rootNode, "profile_image");

        return new Employee(id, name, salary, age, profileImage);
    }

    /* The DummyRestAPI Response doesn't follow consistent naming conventions
    (ie. sometime employee_name in the JSON Response is just called name).
    This method will map both naming conventions
    */
    public String fetchJsonNodeAlias(JsonNode rootNode, String key, String alias) {
        String value = getJsonNodeText(rootNode, key);
        if (value == null) {
            value = getJsonNodeText(rootNode, alias);
        }
        return value;
    }

    private String getJsonNodeText(JsonNode rootNode, String key) {
        JsonNode node = rootNode.get(key);
        return node != null ? node.asText(null) : null;
    }
}
