package com.flynas.api.base;

import com.flynas.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static io.restassured.RestAssured.given;

public class APIBase {
    private static Logger log = LogManager.getLogger(APIBase.class);
    private static RequestSpecification baseRequestSpec;
    private static ResponseSpecification responseSpec;

    private static final ThreadLocal<ConcurrentHashMap<String, String>> sessionTokenContainer =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static String getSessionToken(String testcaseId)
    {
        return sessionTokenContainer.get().get(testcaseId);
    }
    public static void setSessionToken(String testCaseId,String sessionToken)
    {
        sessionTokenContainer.get().put(testCaseId,sessionToken);
    }
    public static void clearSessionToken(){
        sessionTokenContainer.get().clear();
    }

    @Parameters({"env"})
    @BeforeSuite
    public static RequestSpecification setupAPI(){
        log.info("Initializing API Automation Testing Layer Environment...");

        String targetAPIUrl = ConfigReader.getProperty("api.baseurl");
        if(targetAPIUrl!=null){
            RestAssured.baseURI = targetAPIUrl;
            log.info("Global API BaseURI mapped successfully to: " + RestAssured.baseURI);
        }
        else {
            log.warn("'api.baseurl' property missing");
        }
        baseRequestSpec = new RequestSpecBuilder()
                .setBaseUri(targetAPIUrl)
                .setContentType(ContentType.JSON)
                .build();
        return baseRequestSpec;
    }
    public static ResponseSpecification getSuccessResponseSpec(){
        responseSpec=new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
        return responseSpec;
    }

}
