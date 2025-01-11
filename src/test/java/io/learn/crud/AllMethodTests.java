package io.learn.crud;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
public class AllMethodTests {

    // https://petstore.swagger.io/v2/pet/6789

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                                                .setContentType("application/json").build();
    }

    // GET
    @Test
    public void testGET() {
        get("/pet/6789")
                .then().statusCode(200);
    }

    // DELETE
    @Test
    public void testDELETE() {
        delete("/pet/6789")
                .then().statusCode(200);
    }

    // POST
    @Test
    public void testPOST() {

        String requestBody = "{\"category\":{\"id\":1,\"name\":\"dog\"},\"name\":\"doggie-restassured\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
        Response response = given()
                                .body(requestBody)
                            .when()
                                .post("/pet");

        String id = response.path("id").toString();
        System.out.println("New Pet created with id: " +id);
    }

    // PUT
    @Test
    public void testPUT() {

        String requestBody = "{\"id\":9223372036854743269,\"category\":{\"id\":1,\"name\":\"dog\"},\"name\":\"doggie-restassured-updated\",\"photoUrls\":[\"string\"],\"tags\":[{\"id\":0,\"name\":\"string\"}],\"status\":\"available\"}";
        Response response = given()
                                .body(requestBody)
                            .when()
                                .put("/pet");

        String id = response.path("id").toString();
        String name = response.path("name").toString();
        System.out.println("Pet with id " +id+ " updated with name: " +name);
    }
}
