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

public class UpdateBookingTest extends BaseTest
{
    @Test
    public void updateBookingTest()
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

        //Update details
        String updatedFirstName = "Vick";
        String updatedLastName = "Mann";
        int updatedTotalPrice = 789;
        boolean updatedDepositPaid = false;
        String updatedCheckInDate = "2026-01-02";
        String updatedCheckOutDate = "2026-02-02";
        String updatedAdditionalNeeds = "None";

        //Create JSON body
        requestData.replace("firstname", updatedFirstName);
        requestData.replace("lastname", updatedLastName);
        requestData.replace("totalprice", updatedTotalPrice);
        requestData.replace("depositpaid", updatedDepositPaid);
        requestData.replace("checkin", updatedCheckInDate);
        requestData.replace("checkout", updatedCheckOutDate);
        requestData.replace("additionalneeds", updatedAdditionalNeeds);

        requestBody = getRequestBody(requestData);

        String username = "admin";
        String password = "password123";

        response = RestAssured.given().auth().preemptive().basic(username, password).contentType(ContentType.JSON).body(requestBody.toString()).put(this.updateBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Validating Updated Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);


        //Requesting Booking by ID
        response = RestAssured.get(this.getBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);

        softAssert.assertAll();
    }

    @Test
    public void updateBookingWithCookieTest()
    {
        Response response;
        JSONObject requestBody;

        Map<String, Object> requestData = new HashMap<>();

        String url = "https://restful-booker.herokuapp.com/booking";

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

        //Update details
        String updatedFirstName = "Vick";
        String updatedLastName = "Mann";
        int updatedTotalPrice = 789;
        boolean updatedDepositPaid = false;
        String updatedCheckInDate = "2026-01-02";
        String updatedCheckOutDate = "2026-02-02";
        String updatedAdditionalNeeds = "None";

        //Create Token
        JSONObject tokenBody = new JSONObject();
        tokenBody.put("username", "admin");
        tokenBody.put("password", "password123");

        response = RestAssured.given().contentType(ContentType.JSON).body(tokenBody.toString()).post(this.createToken);
        Assert.assertEquals(response.getStatusCode(), 200);
        String token = response.jsonPath().getString("token");

        System.out.println(token);

        //Create JSON body
        requestData.replace("firstname", updatedFirstName);
        requestData.replace("lastname", updatedLastName);
        requestData.replace("totalprice", updatedTotalPrice);
        requestData.replace("depositpaid", updatedDepositPaid);
        requestData.replace("checkin", updatedCheckInDate);
        requestData.replace("checkout", updatedCheckOutDate);
        requestData.replace("additionalneeds", updatedAdditionalNeeds);

        requestBody = getRequestBody(requestData);

        response = RestAssured.given().contentType(ContentType.JSON).header("Cookie", "token=" + token).body(requestBody.toString()).put(this.updateBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Validating Updated Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);

        //Requesting Booking by ID
        response = RestAssured.get(this.getBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);

        softAssert.assertAll();
    }

    @Test
    public void updateBookingWithAuthTest()
    {
        Response response;
        JSONObject requestBody;

        Map<String, Object> requestData = new HashMap<>();

        String url = "https://restful-booker.herokuapp.com/booking";

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

        //Update details
        String updatedFirstName = "Vick";
        String updatedLastName = "Mann";
        int updatedTotalPrice = 789;
        boolean updatedDepositPaid = false;
        String updatedCheckInDate = "2026-01-02";
        String updatedCheckOutDate = "2026-02-02";
        String updatedAdditionalNeeds = "None";

        //Create JSON body
        requestData.replace("firstname", updatedFirstName);
        requestData.replace("lastname", updatedLastName);
        requestData.replace("totalprice", updatedTotalPrice);
        requestData.replace("depositpaid", updatedDepositPaid);
        requestData.replace("checkin", updatedCheckInDate);
        requestData.replace("checkout", updatedCheckOutDate);
        requestData.replace("additionalneeds", updatedAdditionalNeeds);

        requestBody = getRequestBody(requestData);

        String auth = "Basic YWRtaW46cGFzc3dvcmQxMjM=";

        response = RestAssured.given().contentType(ContentType.JSON).header("Authorization", auth).body(requestBody.toString()).put(this.updateBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Validating Updated Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);

        //Requesting Booking by ID
        response = RestAssured.get(this.getBooking + bookingID);
        response.print();
        Assert.assertEquals(response.getStatusCode(), 200);

        //Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), updatedFirstName);
        softAssert.assertEquals(response.jsonPath().getString("lastname"), updatedLastName);
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), updatedTotalPrice);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), updatedDepositPaid);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), updatedCheckInDate);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), updatedCheckOutDate);
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), updatedAdditionalNeeds);

        softAssert.assertAll();
    }
}
