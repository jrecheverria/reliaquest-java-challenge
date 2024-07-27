# Coding Challenge Solution

In this coding challenge, I have successfully implemented the controller methods declared in the `IEmployeeController` interface. Below, you'll find details about the solution, including handling rate limiting, logging, and unit tests.

## Rate Limiting Handling

Please be aware that the external service `https://dummy.restapiexample.com` imposes heavy rate limits on incoming requests. To manage this, I have implemented a global exception handler that catches rate limit errors and sends a generic error message back to the client:

```json
"An error occurred while calling an external service."
```

## Logging

Logging has been implemented for most critical operations using Log4j. Log output is directed to the logs folder, providing detailed information about the application's behavior and facilitating debugging and monitoring.

## Unit Tests

Unit tests have been written to ensure the robustness and reliability of the EmployeeService and EmployeeManager classes. These tests cover various scenarios and edge cases, verifying that the implemented methods function as expected.

## Insomnia/Postman Collection

For convenience, I have included a file named ReliaQuest-Java-Insomnia-Collection.json. This file contains a collection of JSON requests that you can use to interact with the endpoints easily. You can import this JSON file into either Insomnia or Postman.

## How to Use
1. Import the JSON Collection: Import the ReliaQuest-Java-Insomnia-Collection.json file into Insomnia or Postman to interact with the implemented endpoints effortlessly.
2. Run the Application: Start the application as you normally would.
3. Check Logs: Monitor the logs folder for detailed information about the application's operations and any potential issues.
4. Execute Unit Tests: Run the unit tests to ensure that all functionalities are working correctly and to verify the integrity of the application.