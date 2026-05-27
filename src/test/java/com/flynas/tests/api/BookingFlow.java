package com.flynas.tests.api;

import com.flynas.api.base.APIBase;
import com.flynas.api.payloads.FlightDetails;
import com.flynas.api.payloads.FlightSearchRequest;
import com.flynas.api.payloads.FlightSelectRequest;
import com.flynas.clients.SessionClient;
import com.flynas.utils.APIUtils;
import com.flynas.utils.ApiDataProvider;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BookingFlow extends APIBase {
    private static  final Logger log = LogManager.getLogger(BookingFlow.class);
    private SessionClient sessionClient;
    private String sessionToken;
    //private Response flightSearchResponse;
    private static final ThreadLocal<ConcurrentHashMap<String, Response>> flightSearchResponse =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    @Test(priority = 1, description = "generating session token",dataProvider = "BookingFlowExcelData",dataProviderClass = ApiDataProvider.class)
    public void verifySessionToken(Map<String, String> dataMap){
        String testCaseId = dataMap.get("TestCase");
       sessionClient = new SessionClient();
       Response response=sessionClient.sessionCreate();
       sessionToken=response.header("X-Session-Token");
        Assert.assertNotNull(sessionToken,"session token is null");
        setSessionToken(testCaseId,sessionToken);
        log.info("session Token is "+getSessionToken(testCaseId));
    }
    @Test(priority = 2, dependsOnMethods = "verifySessionToken", description = "flight search API execution"
    ,dataProvider = "BookingFlowExcelData",dataProviderClass = ApiDataProvider.class)
    public void flightSearch(Map<String,String> dataMap) throws IOException {
        String testCaseId = dataMap.get("TestCase");

        List<FlightDetails> flights = new ArrayList<>();
        flights.add(new FlightDetails(dataMap.get("Origin"),dataMap.get("Destination"),dataMap.get("Date")));
        flights.add(new FlightDetails(dataMap.get("Origin"),dataMap.get("Destination"),dataMap.get("Date")));

        int adult=Integer.parseInt(dataMap.get("Pax").split(",")[0]);
        int child=Integer.parseInt(dataMap.get("Pax").split(",")[1]);
        int infant=Integer.parseInt(dataMap.get("Pax").split(",")[2]);
        log.info("initiating API test case "+dataMap.get("TestCase"));
        FlightSearchRequest.FlightSearchBody flightSearchBody = new FlightSearchRequest.FlightSearchBody(null,adult,child,dataMap.get("TripType"),flights,infant,dataMap.get("Currency"));
        FlightSearchRequest flightSearchRequest = new FlightSearchRequest(flightSearchBody);
        sessionClient = new SessionClient();
        Response response =sessionClient.flightSearch(flightSearchRequest,getSessionToken(testCaseId));
        flightSearchResponse.get().put(testCaseId,response);
        response.then().log().all().spec(getSuccessResponseSpec());
        APIUtils.saveResponseToFile(response,testCaseId+"flightSearchResponse.json");
    }
    @Test(priority = 3,dependsOnMethods = "flightSearch",description = "flight select API execution"
    ,dataProvider = "BookingFlowExcelData",dataProviderClass = ApiDataProvider.class)
    public void flightSelect(Map<String,String> dataMap) throws IOException {
        String testCaseId = dataMap.get("TestCase");
        String currentToken = getSessionToken(testCaseId);
        Response currentSearchResponse = flightSearchResponse.get().get(testCaseId);
        String bundleCode=dataMap.get("BundleCode");
        String segment =dataMap.get("Segments");
        JsonPath jsonPath = currentSearchResponse.jsonPath();
        String journeyKey = jsonPath.getString("flightsAvailability.trips[0].flights[0].journeyKey");
        String fareKey = jsonPath.getString("flightsAvailability.trips[0].flights[0].fares[0].fareKey");

        String compositeKeys = fareKey+"|"+journeyKey+"|"+bundleCode+"|"+segment;
         List<String> key = new ArrayList<>();
         key.add(compositeKeys);
        FlightSelectRequest.MarketSellBody marketSellBody = new FlightSelectRequest.MarketSellBody(key,true);
        FlightSelectRequest flightSelectRequest = new FlightSelectRequest(marketSellBody);
        sessionClient = new SessionClient();
        Response response=sessionClient.flightSelect(flightSelectRequest,currentToken);
        response.then().log().all().spec(getSuccessResponseSpec());
    }
    @AfterClass(alwaysRun = true)
    public void tearDownSuiteContext() {
        // Safe clear memory caches
        flightSearchResponse.get().clear();
        clearSessionToken();
    }
}
