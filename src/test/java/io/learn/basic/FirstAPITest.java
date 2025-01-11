package io.learn.basic;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FirstAPITest {

    @Test
    public void testUsersAPI() {
        // End-point: https://jsonplaceholder.typicode.com/users/1

        // given -> prepare the request object (end-point, headers, cookies, auth, etc.)

        // when -> perform an action (calling the server using correct HTTP method - GET/PUT/POST/DELETE)

        // then -> verification of the response object (status code, body, headers, etc.,)

        given()
                .baseUri("https://jsonplaceholder.typicode.com") // given
        .when()
                .get("/users/1")                            // when
        .then()
                .statusCode(200)                // then
                .body("username", equalTo("Bret"));
    }
}
