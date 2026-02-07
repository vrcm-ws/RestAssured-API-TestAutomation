package com.herokuapp.restfulbooker.tests;

import com.herokuapp.restfulbooker.utilities.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest extends BaseTest
{
    @Parameters("bookingID")
    @Test
    public void getBookingID(@Optional("9") int bookingID)
    {
        //Set PATH Parameters
        specification.pathParam("bookingID", 9);

        String getBookingSuffix = "/booking/";
        Response response = RestAssured.given(specification).get(getBookingSuffix + "{bookingID}");
        response.print();

        //Status Code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code: 200");

        //Validating Data
        softAssert.assertEquals(response.jsonPath().getString("firstname"), "Jim");
        softAssert.assertEquals(response.jsonPath().getString("lastname"), "Jackson");
        softAssert.assertEquals(response.jsonPath().getInt("totalprice"), 799);
        softAssert.assertEquals(response.jsonPath().getBoolean("depositpaid"), false);
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkin"), "2017-06-08");
        softAssert.assertEquals(response.jsonPath().getString("bookingdates.checkout"), "2023-10-16");
        softAssert.assertEquals(response.jsonPath().getString("additionalneeds"), "Breakfast");

        //softAssert.assertAll();
    }
}
