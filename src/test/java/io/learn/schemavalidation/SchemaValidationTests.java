package io.learn.schemavalidation;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class SchemaValidationTests {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType("application/json").build();
    }

    @Test
    public void testSchemaForPet() {
        Map<String, Object> petData = getPetData();

        // create a new pet - POST
        String newPetID = given().body(petData).when().post("/pet").path("id").toString();
        System.out.println("New Pet created is: " + newPetID);

        // verify the new pet - GET
        get("/pet/" + newPetID).then().statusCode(200)
                .body("status", equalTo("pending"))
                .body(matchesJsonSchemaInClasspath("PetSchema.json"));
    }

    private Map<String, Object> getPetData() {
        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", 1);
        categoryMap.put("name", "dog");

        Map<String, Object> petMap = new HashMap<>();
        petMap.put("name", "doggie");
        petMap.put("status", "pending");
        petMap.put("category", categoryMap);
        return petMap;
    }
}
