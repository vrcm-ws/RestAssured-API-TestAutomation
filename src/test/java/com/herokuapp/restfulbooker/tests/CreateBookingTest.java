package com.herokuapp.restfulbooker.tests;

import com.herokuapp.restfulbooker.utilities.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateBookingTest extends BaseTest
{
    @Test
    public void createBookingTest()
    {
        Response response;
        JSONObject requestBody;
        Map<String, Object> requestData = new HashMap<>();

        String firstName = "Vico";
        String lastName = "Bot";
        int totalPrice = 456;
        boolean depositPaid = true;
        String checkInDate = "2026-01-01";
        String checkOutDate = "2026-02-01";
        String additionalNeeds = "Entertainment";

        //Create JSON body
        requestData.put("firstname", firstName);
        requestData.put("lastname", lastName);
        requestData.put("totalprice", totalPrice);
        requestData.put("depositpaid", depositPaid);
        requestData.put("checkin", checkInDate);
        requestData.put("checkout", checkOutDate);
        requestData.put("additionalneeds", additionalNeeds);

        requestBody = getRequestBody(requestData);

        response = RestAssured.given().contentType(ContentType.JSON).body(requestBody.toString()).post(this.createBooking);
        Assert.assertEquals(response.getStatusCode(), 200);

        int bookingID = response.jsonPath().getInt("bookingid");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("booking.firstname"), firstName);
        softAssert.assertEquals(response.jsonPath().getString("booking.lastname"), lastName);
        softAssert.assertEquals(response.jsonPath().getInt("booking.totalprice"), totalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("booking.depositpaid"), depositPaid);
        softAssert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkin"), checkInDate);
        softAssert.assertEquals(response.jsonPath().getString("booking.bookingdates.checkout"), checkOutDate);
        softAssert.assertEquals(response.jsonPath().getString("booking.additionalneeds"), additionalNeeds);

        //Requesting Booking by ID
        response = RestAssured.get(this.getBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 200);

        //Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), firstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), lastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), totalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), depositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), checkInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), checkOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), additionalNeeds);

        softAssert.assertAll();

    }
}
