package io.learn.basic;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BasicTests {

    @Test
    public void verifyStatusCode() {
        // http://api.zippopotam.us/us/90210

        given()
                .baseUri("http://api.zippopotam.us")
        .when()
                .get("/us/90210")
        .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void verifyCountryDetails() {
        given()
                .baseUri("http://api.zippopotam.us")
        .when()
                .get("/us/90210")
        .then()
                .assertThat().statusCode(200)
                .body("country", equalTo("United States")) // JSON object
                .body("'country abbreviation'", equalTo("US"))
                .body("'post code'", equalTo("90210"));
    }

    @Test
    public void verifyPlacesDetails() {
        given()
                .baseUri("http://api.zippopotam.us")
        .when()
                .get("/us/90210")
        .then()
                .assertThat().statusCode(200)
                .body("places[0].state", equalTo("California")) // JSON Array
                .body("places[0].longitude", equalTo("-118.4065"))
                .body("places[0].latitude", equalTo("34.0901"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
                .body("places[0].'state abbreviation'", equalTo("CA"));
    }

    @Test
    public void verifyNegativeTest() {
        given()
                .baseUri("http://api.zippopotam.us")
        .when()
                .get("/us/99999")
        .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void verifyResponseType() {
        given()
                .baseUri("http://api.zippopotam.us")
        .when()
                .get("/us/90210")
        .then()
                .assertThat().statusCode(200)
                .contentType(equalTo("application/json"));
    }
}
