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

public class DeleteBookingTest extends BaseTest
{
    @Test
    public void deleteBookingTest()
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
        response = createBooking(requestBody);
        response.print();

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
        softAssert.assertAll();

        //Deleting Booking
        String username = "admin";
        String password = "password123";

        response = RestAssured.given().auth().preemptive().basic(username, password).delete(this.deleteBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 201);

        //Validating Booking ID is not found
        response = RestAssured.get(this.getBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteBookingWithCookieTest()
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
        response = createBooking(requestBody);
        response.print();

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
        softAssert.assertAll();

        //Create Token
        JSONObject tokenBody = new JSONObject();
        tokenBody.put("username", "admin");
        tokenBody.put("password", "password123");

        response = RestAssured.given().contentType(ContentType.JSON).body(tokenBody.toString()).post(this.createToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        String token = response.jsonPath().getString("token");

        //Deleting Booking
        response = RestAssured.given().contentType(ContentType.JSON).header("Cookie", "token=" + token).delete(deleteBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 201);

        //Validating Booking ID is not found
        response = RestAssured.get(this.getBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteBookingWithAuthTest()
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
        response = createBooking(requestBody);
        response.print();

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
        softAssert.assertAll();

        //Deleting Booking
        String auth = "Basic YWRtaW46cGFzc3dvcmQxMjM=";

        response = RestAssured.given().contentType(ContentType.JSON).header("Authorization", auth).delete(deleteBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 201);

        //Validating Booking ID is not found
        response = RestAssured.get(this.getBooking + bookingID);
        Assert.assertEquals(response.getStatusCode(), 404);
        Assert.assertEquals(response.getBody().asString(), "Not Found");
    }
}
