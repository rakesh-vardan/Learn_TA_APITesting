package io.learn.auth;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubAPITests {

    private RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://api.github.com")
            .setContentType("application/json")
            .build();

    @Test
    public void testCase1() {
        given().
                spec(requestSpecification)
        .when()
                .get("/users/rakesh-vardan/repos")
        .then()
                .statusCode(200);
    }

    @Test
    public void testCompleteFlow() {

        String userName = "rakesh-vardan";
        String repoName = "my-dummy-repo-test";
        String payLoad = "{\"name\":\""+repoName+"\",\"description\":\"This is a new repository\",\"homepage\":\"https://github.com\",\"private\":false,\"is_template\":false}";
        String oAuth2Token = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX"; // use your access token

        // create a new repo
        given().
                spec(requestSpecification)
                .body(payLoad)
                .auth()
                .oauth2(oAuth2Token)
                .log().all()
        .when()
                .post("/user/repos")
        .then()
                .statusCode(201)
                        .log().all();

        // delete that repo
        given().
                spec(requestSpecification)
                .auth()
                .oauth2(oAuth2Token)
                .log().all()
        .when()
                .delete("/repos/"+userName+"/" + repoName)
        .then()
                .statusCode(204)
                .log().all();
    }
}
