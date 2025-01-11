package io.learn.auth;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FootballAPITests {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.football-data.org";
        RestAssured.basePath = "/v4";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json")
                .addHeader("X-Auth-Token", "XXXXXXXXXXXXXXXX") // use your API Key
                .build();
    }

    @Test
    public void testCase1() {
        get("/teams")
                .then().statusCode(200);
    }

    @Test
    public void testCase2() {
        get("/teams/66")
                .then().statusCode(200)
                .body("name", equalTo("Manchester United FC"));
    }
}
