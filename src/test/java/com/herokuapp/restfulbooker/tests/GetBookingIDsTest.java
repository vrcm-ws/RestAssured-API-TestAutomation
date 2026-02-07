package com.herokuapp.restfulbooker.tests;

import com.herokuapp.restfulbooker.utilities.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GetBookingIDsTest extends BaseTest
{
    @Test
    public void getBookingIDsWithoutFilterTest()
    {
        //Get response with booking IDs
        Response response = RestAssured.get(this.getBookingIDs);
        response.print();

        //Verify response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Verifying at least 1 Booking ID in response
        List<Integer> bookingIDs = response.jsonPath().getList("bookingid");

        Assert.assertFalse(bookingIDs.isEmpty(), "There should be at least 1 Booking ID");
    }

    @Test
    public void getBookingIDsWithFilterTest()
    {
        //Add Query Parameter
        specification.queryParam("firstname", "Jim");

        //Get response with booking IDs
        //Response response = RestAssured.get(this.getBookingIDs + "?firstname=Jim");
        Response response = RestAssured.given(specification).get("/booking");

        response.print();
        List<Integer> ids = response.jsonPath().getList("bookingId");
        System.out.println(ids.size());

        //Verify response
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Verifying at least 1 Booking ID in response
        List<Integer> bookingIDs = response.jsonPath().getList("bookingid");

        Assert.assertFalse(bookingIDs.isEmpty(), "There should be at least 1 Booking ID");
    }
}
