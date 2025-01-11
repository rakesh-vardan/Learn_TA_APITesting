package io.learn.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class SpecificationTests {

    private RequestSpecification requestSpecification = new RequestSpecBuilder()
                        .setBaseUri("http://api.zippopotam.us")
                        .setContentType("application/json")
                        .build();

    private ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType("application/json")
                        .expectResponseTime(lessThan(2000L))
                        .build();

    private ResponseSpecification responseSpecificationError = new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .expectContentType("application/json")
                        .expectResponseTime(lessThan(2000L))
                        .build();

    @Test
    public void verifyStatusCode() {
        // http://api.zippopotam.us/us/90210

        given()
                .spec(requestSpecification)
        .when()
                .get("/us/90210")
        .then()
                .spec(responseSpecification);
    }

    @Test
    public void verifyCountryDetails() {
        given()
                .spec(requestSpecification)
        .when()
                .get("/us/90210")
        .then()
                .spec(responseSpecification)
                .body("country", equalTo("United States")) // JSON object
                .body("'country abbreviation'", equalTo("US"))
                .body("'post code'", equalTo("90210"));
    }

    @Test
    public void verifyPlacesDetails() {
        given()
                .spec(requestSpecification)
        .when()
                .get("/us/90210")
        .then()
                .spec(responseSpecification)
                .body("places[0].state", equalTo("California")) // JSON Array
                .body("places[0].longitude", equalTo("-118.4065"))
                .body("places[0].latitude", equalTo("34.0901"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .body("places[0].'state abbreviation'", equalTo("CA"));
    }

    @Test
    public void verifyNegativeTest() {
        given()
                .spec(requestSpecification)
        .when()
                .get("/us/99999")
        .then()
                .spec(responseSpecificationError);
    }

    @Test
    public void verifyResponseType() {
        given()
                .spec(requestSpecification)
        .when()
                .get("/us/90210")
        .then()
                .spec(responseSpecification);
    }
}
