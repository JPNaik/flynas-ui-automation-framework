package com.flynas.api.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;

public class APIBase {
    RequestSpecification requestSpecification;

    @BeforeTest
    public void setupAPI(){
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://ibestage.flynas.com/PreLiveTestR4Y/api/")
                .setContentType(ContentType.JSON)
                .build();
    }
    public String getSessionToken(){
        Response response=given().spec(requestSpecification).log().all()
                .when().post("SessionCreate")
                .then().log().all().assertThat().statusCode(201).extract().response();
        String sessionToken = response.header("X-Session-Token");

        return sessionToken;
    }

}
