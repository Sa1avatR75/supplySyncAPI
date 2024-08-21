package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.Config;
import utilities.SupplySyncToken;

import java.util.HashMap;
import java.util.Map;

public class CompanySteps {
    private RequestSpecification request;
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private String token = SupplySyncToken.getToken();
    private final String baseURl = Config.getProperty("supplySyncAPIURL");
    private CustomResponse customResponse;

    @Given("supplySync base URL")
    public void supply_sync_base_url() {

       request = RestAssured.given().baseUri(baseURl);
    }
    @Given("I provide VALID authorization token")
    public void i_provide_valid_authorization_token() {
       request.auth().oauth2(token).contentType(ContentType.JSON);
    }
    @When("I send a POST request to create a company with name {string}, address {string}, phone number {string}, and email {string}")
    public void i_send_a_post_request_to_create_a_company_with_name_address_phone_number_and_email(String name, String address, String number, String email) throws JsonProcessingException {
        RequestBody requestBody = new RequestBody();
        requestBody.setName(name);
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(number);
        requestBody.setEmail(email);

        response = request.body(requestBody).post("/api/v1/company");

        customResponse = mapper.readValue(response.asString(),CustomResponse.class);


    }
    @When("I send a POST request to create a company with name {string}, address {string}, phone number {string}, and wrongEmail {string}")
    public void i_send_a_post_request_to_create_a_company_with_name_address_phone_number_and_wrong_email(String name, String address, String number, String wrongEmail) throws JsonProcessingException {
        RequestBody requestBody = new RequestBody();
        requestBody.setName(name);
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(number);
        requestBody.setEmail(wrongEmail);

        response = request.body(requestBody).post("/api/v1/company");

        customResponse = mapper.readValue(response.asString(),CustomResponse.class);


    }

    @When("I send a POST request to create a company with logo {string},  name {string}, address {string}, phone number {string}, and email {string}")
    public void i_send_a_post_request_to_create_a_company_with_logo_name_address_phone_number_and_email(String logo, String name, String address, String number, String email) throws JsonProcessingException {

        RequestBody requestBody = new RequestBody();
        requestBody.setLogo(logo);
        requestBody.setName(name);
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(number);
        requestBody.setEmail(email);

        response = request.body(requestBody).post("/api/v1/company");

        customResponse = mapper.readValue(response.asString(),CustomResponse.class);


    }
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer int1) {
        Assert.assertEquals((int)int1,response.statusCode());
    }
    @Then("the response should contain the logo {string}")
    public void the_response_should_contain_the_logo(String logo) {
        Assert.assertEquals(logo,customResponse.getLogo());
    }
    @Then("the response should contain the name {string}")
    public void the_response_should_contain_the_name(String name) {
        Assert.assertEquals(name,customResponse.getName());
    }
    @Then("the response should contain the address {string}")
    public void the_response_should_contain_the_address(String address) {
        Assert.assertEquals(address, customResponse.getAddress());
    }
    @Then("the response should contain the phone number {string}")
    public void the_response_should_contain_the_phone_number(String number) {
        Assert.assertEquals(number, customResponse.getPhoneNumber());
    }
    @Then("the response should contain the email {string}")
    public void the_response_should_contain_the_email(String email) {
        Assert.assertEquals(email , customResponse.getEmail());
    }
    @Then("the response should contain a id field")
    public void the_response_should_contain_a_id_field() {
    Assert.assertTrue(customResponse.getId() != 0);
    }
    @Given("I do not have a valid authentication token")
    public void i_do_not_have_a_valid_authentication_token() {
        request.auth().oauth2("token").contentType(ContentType.JSON);
    }
    @When("I send a POST request to create a company with address {string}, phone number {string}, and email {string}")
    public void i_send_a_post_request_to_create_a_company_with_address_phone_number_and_email(String address, String number, String email) throws JsonProcessingException {

        RequestBody requestBody = new RequestBody();
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(number);
        requestBody.setEmail(email);

        response = request.body(requestBody).post("/api/v1/company");

        customResponse = mapper.readValue(response.asString(),CustomResponse.class);

    }

}
