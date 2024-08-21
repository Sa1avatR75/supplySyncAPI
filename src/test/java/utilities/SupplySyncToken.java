package utilities;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SupplySyncToken {
    public static String getToken(){
        String endPoint = Config.getProperty("supplySyncAPIURL") + "/api/v1/public/login";

        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("admin@codewise.com");
        requestBody.setPassword("codewise123");

        Response response = RestAssured.given().contentType(ContentType.JSON).body(requestBody).post(endPoint);
        return response.jsonPath().getString("jwtToken");
    }
}
