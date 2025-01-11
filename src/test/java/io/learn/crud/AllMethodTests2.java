package io.learn.crud;

import io.learn.crud.models.Category;
import io.learn.crud.models.Pet;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AllMethodTests2 {

    // https://petstore.swagger.io/v2/pet/6789

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io";
        RestAssured.basePath = "/v2";

        RestAssured.requestSpecification = new RequestSpecBuilder()
                                                .setContentType("application/json").build();
    }


    // POST
    @Test
    public void testPOST() {

        Category category = new Category(1, "dog");
        Pet pet = new Pet(12345, "snoopie", category,"pending");

        Response response = given()
                                .body(pet) // serialization process (Java object to text)
                            .when()
                                .post("/pet");

        String id = response.path("id").toString();
        System.out.println("New Pet created with id: " +id);
    }

    @Test
    public void testDeserialization() {
        Map<String, Object> petData = getPetData();

        // create a new pet - POST
        String newPetID = given().body(petData).when().post("/pet").path("id").toString();
        System.out.println("New Pet created is: " +newPetID);

        // verify the new pet - GET
        Response response = get("/pet/" + newPetID);
        Pet pet = response.as(Pet.class); // Deserialization (text to Java Object)

        System.out.println("Pet name is: " +pet.getName());
        System.out.println("Pet status is: " +pet.getStatus());
    }

    // PUT
    @Test
    public void testPUT() {
        Category category = new Category(1, "dog");
        Pet pet = new Pet(12345, "snoopie", category,"available");
        Response response = given()
                                .body(pet)
                            .when()
                                .put("/pet");

        String id = response.path("id").toString();
        String name = response.path("name").toString();
        System.out.println("Pet with id " +id+ " updated with name: " +name);
    }

    @Test
    public void testAllMethods() {
        Map<String, Object> petData = getPetData();

        // create a new pet - POST
        String newPetID = given().body(petData).when().post("/pet").path("id").toString();
        System.out.println("New Pet created is: " +newPetID);

        // verify the new pet - GET
        get("/pet/" +newPetID).then().statusCode(200)
                .and()
                .body("status", equalTo("pending"));

        // update the pet - PUT
        petData.put("status", "available");
        petData.put("id", newPetID);
        given().body(petData).when().put("/pet").then().statusCode(200);

        // verify the pet with updated status - GET
        get("/pet/" +newPetID).then().statusCode(200)
                .and()
                .body("status", equalTo("available"));

        // delete the pet - DELETE
        delete("/pet/" +newPetID).then().statusCode(200);

        // verify the deleted pet - GET
        get("/pet/" +newPetID).then().statusCode(404);
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
