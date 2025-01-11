# API Testing Project

This project is designed for API testing using REST Assured and TestNG. It provides a framework to automate the testing of RESTful APIs.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) installed (version 8 or higher)
- Maven installed
- An IDE such as IntelliJ IDEA or Eclipse

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/rakesh-vardan/Learn_TA_APITesting.git
    ```
2. Navigate to the project directory:
    ```sh
    cd APITesting
    ```
3. Install the dependencies:
    ```sh
    mvn clean install
    ```

## Running Tests

To run the tests, use the following command:
```sh
mvn test
```

## Project Structure

- `src/test/java`: Contains the test cases written using REST Assured and TestNG.
- `src/test/resources`: Contains any configuration files or test data.

## Writing Tests

To write a new test, create a new class in the `src/test/java` directory and use REST Assured methods to define your API requests and assertions. Annotate your test methods with `@Test` from TestNG.

Example:
```java
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ExampleTest {

    @Test
    public void testGetEndpoint() {
        RestAssured.baseURI = "https://api.example.com";
        Response response = RestAssured.given()
            .when()
            .get("/endpoint")
            .then()
            .statusCode(200)
            .body("key", equalTo("value"))
            .extract()
            .response();
    }
}
```
