package com.herokuapp.restfulbooker.tests;

import com.herokuapp.restfulbooker.utilities.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest
{
    @Test
    public void healthCheckTest()
    {
        given()
            .spec(specification).
        when().
            get("/ping").
        then().
            assertThat().
            statusCode(201);
    }

    @Test
    public void headersTest()
    {
        Header someHeader = new Header("someName", "someValue");

        Response response = RestAssured.given(specification).header(someHeader).get("/ping");

        //Get Headers
        Headers headers = response.getHeaders();
        System.out.println(headers);

        Header headerA = headers.get("Server");

        System.out.println(headerA.getName());
        System.out.println(headerA.getValue());

        String headerB = response.getHeader("Server");
        System.out.println(headerB);
    }

    @Test
    public void cookiesTest()
    {
        Response response;

        Cookie someCookie = new Cookie.Builder("someName", "someValue").build();
        Header someHeader = new Header("someName", "someValue");

        response = RestAssured.given(specification).cookie(someCookie).header(someHeader).log().all().get("/ping");

        //Get Cookies
        Cookies cookies = response.getDetailedCookies();
        System.out.println(cookies);
    }
}
