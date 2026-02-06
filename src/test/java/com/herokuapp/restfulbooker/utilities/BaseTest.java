package com.herokuapp.restfulbooker.utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.util.Map;
import java.util.Optional;

public class BaseTest
{
    protected SoftAssert softAssert;
    protected RequestSpecification specification;

    protected String createBooking = "https://restful-booker.herokuapp.com/booking";
    protected String getBookingIDs = "https://restful-booker.herokuapp.com/booking";
    protected String getBooking = "https://restful-booker.herokuapp.com/booking/";
    protected String updateBooking = "https://restful-booker.herokuapp.com/booking/";
    protected String createToken = "https://restful-booker.herokuapp.com/auth";
    protected String partialBookingUpdate = "https://restful-booker.herokuapp.com/booking/";
    protected String deleteBooking = "https://restful-booker.herokuapp.com/booking/";

    protected String baseURL = "https://restful-booker.herokuapp.com";

    @BeforeMethod
    public void methodSetup()
    {
        softAssert = new SoftAssert();
        specification = new RequestSpecBuilder().setBaseUri(baseURL).build();
    }

    public JSONObject getRequestBody(Map<String, Object> bookingDetails)
    {
        JSONObject body = new JSONObject();
        JSONObject dates = new JSONObject();

        Optional.ofNullable(bookingDetails.get("firstname")).ifPresent(value -> body.put("firstname", value));
        Optional.ofNullable(bookingDetails.get("lastname")).ifPresent(value -> body.put("lastname", value));
        Optional.ofNullable(bookingDetails.get("totalprice")).ifPresent(value -> body.put("totalprice", value));
        Optional.ofNullable(bookingDetails.get("depositpaid")).ifPresent(value -> body.put("depositpaid", value));
        Optional.ofNullable(bookingDetails.get("checkin")).ifPresent(value -> dates.put("checkin", value));
        Optional.ofNullable(bookingDetails.get("checkout")).ifPresent(value -> dates.put("checkout", value));

        if (!dates.isEmpty())
        {
            body.put("bookingdates", dates);
        }

        Optional.ofNullable(bookingDetails.get("additionalneeds")).ifPresent(value -> body.put("additionalneeds", value));

        return body;
    }

    /*POST: New Booking*/
    public Response createBooking(JSONObject bookingDetails)
    {
        return RestAssured.given().contentType(ContentType.JSON).body(bookingDetails.toString()).post(this.createBooking);
    }
}
