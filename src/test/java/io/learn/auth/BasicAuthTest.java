package io.learn.auth;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicAuthTest {

    @Test
    public void testBasicAuth() {
        given()
                .auth()
                .basic("postman", "password")
                .baseUri("https://postman-echo.com")
        .when()
                .get("/basic-auth")
        .then()
                .statusCode(200);
    }

    @Test
    public void testBasicAuthNegative() {
        given()
                .baseUri("https://postman-echo.com")
        .when()
                .get("/basic-auth")
        .then()
                .statusCode(401);
    }
}
