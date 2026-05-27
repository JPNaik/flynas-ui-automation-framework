package com.flynas.clients;

import com.flynas.api.base.APIBase;
import com.flynas.api.payloads.FlightSearchRequest;
import com.flynas.api.payloads.FlightSelectRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class SessionClient extends APIBase {
    private static final String SESSION_ENDPOINT = "/SessionCreate";
    private static final String FLIGHT_SEARCH_ENDPOINT = "/FlightSearch";
    private static final String FLIGHT_SELECT_ENDPOINT = "/FlightSell";

    public Response sessionCreate()
    {
        return given().spec(setupAPI())
                .log().all()
                .when()
                .post(SESSION_ENDPOINT);
    }
    public Response flightSearch(FlightSearchRequest payload,String sessionToken)
    {
        return given().spec(setupAPI())
                .log().all()
                .header("X-Session-Token",sessionToken)
                .body(payload)
                .when()
                .post(FLIGHT_SEARCH_ENDPOINT);
    }
    public Response flightSelect(FlightSelectRequest payload, String sessionToken)
    {
        return given().spec(setupAPI())
                .log().all()
                .header("X-Session-Token",sessionToken)
                .body(payload)
                .when()
                .post(FLIGHT_SELECT_ENDPOINT);
    }

}
