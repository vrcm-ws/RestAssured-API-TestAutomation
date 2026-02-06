package com.herokuapp.restfulbooker.tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest
{
    @Test
    public void healthCheckTest()
    {
        String pingCheck = "https://restful-booker.herokuapp.com/ping";

        given().
        when().
            get(pingCheck).
        then().
            assertThat().
            statusCode(201);
    }
}
