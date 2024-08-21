package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.Config;
import utilities.SupplySyncToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanySteps {
    private RequestSpecification request;
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private String token = SupplySyncToken.getToken();
    private final String baseURl = Config.getProperty("supplySyncAPIURL");
    private CustomResponse customResponse;
    private List<CustomResponse> listCompanies = new ArrayList<>();
    private RequestBody requestBody = new RequestBody();

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
    Assert.assertTrue(!customResponse.getId().isEmpty());
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
    @When("I send a GET request to retrieve all companies")
    public void i_send_a_get_request_to_retrieve_all_companies() {
     response = request.get("/api/v1/company");
    }
    @Then("the response should contain a list of companies")
    public void the_response_should_contain_a_list_of_companies() throws JsonProcessingException {
        CustomResponse customResponse1 = mapper.readValue(response.asString(),CustomResponse.class);
//        System.out.println(customResponse1.getCompany().get(0).getEmail());
        listCompanies = customResponse1.getCompany();
    }
    @Then("each company emails contains {string}")
    public void each_company_emails_contains(String specChar) {
        for (int i = 0; i < listCompanies.size(); i++) {
            if(listCompanies.get(i).getEmail() != null && !listCompanies.get(i).getEmail().isEmpty() ){
               Assert.assertTrue(listCompanies.get(i).getEmail().trim().contains(specChar));


            }

        }
    }

    @When("I send a GET request to retrieve a company with ID {string}")
    public void i_send_a_get_request_to_retrieve_a_company_with_id(String id) throws JsonProcessingException {
        response = request.get("/api/v1/company/"+id);
        customResponse = mapper.readValue(response.asString(),CustomResponse.class);
    }
    @Then("the response should contain the company with ID {string}")
    public void the_response_should_contain_the_company_with_id(String id) {
        Assert.assertEquals(id,customResponse.getId());
    }


    @Given("a company exists with ID {string}")
    public void a_company_exists_with_id(String id) {
        response = request.get("/api/v1/company/"+id);
    }
    @When("I update the company's name to {string}, address to {string}, phone number to {string}, and email to {string}")
    public void i_update_the_company_s_name_to_address_to_phone_number_to_and_email_to(String name, String address, String  number, String email) {
        requestBody.setName(name);
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(number);
        requestBody.setEmail(email);

    }
    @When("I send a PUT request to update the company with ID {string}")
    public void i_send_a_put_request_to_update_the_company_with_id(String id) throws JsonProcessingException {
        response = request.contentType(ContentType.JSON).body(requestBody).put("/api/v1/company/"+id);
        customResponse = mapper.readValue(response.asString(),CustomResponse.class);
    }

    @Then("the response should contain the updated name {string}")
    public void the_response_should_contain_the_updated_name(String name) {
        Assert.assertEquals(name,customResponse.getName());
    }
    @Then("the response should contain the updated address {string}")
    public void the_response_should_contain_the_updated_address(String address) {
        Assert.assertEquals(address,customResponse.getAddress());
    }
    @Then("the response should contain the updated phone number {string}")
    public void the_response_should_contain_the_updated_phone_number(String number) {
        Assert.assertEquals(number,customResponse.getPhoneNumber());
    }
    @Then("the response should contain the updated email {string}")
    public void the_response_should_contain_the_updated_email(String email) {
        Assert.assertEquals(email, customResponse.getEmail());
    }

    @When("I update the company's email to {string}")
    public void i_update_the_company_s_email_to(String wrongEmail) {
        requestBody.setEmail(wrongEmail);
    }
    @Given("I update only the company's phone number to {string}")
    public void i_update_only_the_company_s_phone_number_to(String string) {
       requestBody.setPhoneNumber(string);
    }


    @When("I send a DELETE request to delete the company with ID {string}")
    public void i_send_a_delete_request_to_delete_the_company_with_id(String id) {
        response = request.delete("/api/v1/company/"+ id);
        response.prettyPrint();
    }
    @Then("the response should contain the message {string}")
    public void the_response_should_contain_the_message(String string) {

    }
    @Then("the company with ID {string} should no longer exist in the system")
    public void the_company_with_id_should_no_longer_exist_in_the_system(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }




    @When("I send a GET request to retrieve all companies")
    public void i_send_a_get_request_to_retrieve_all_companies() {
        response = request.get("/api/v1/company");
        response.prettyPrint();
    }
    @Then("the response should contain a list of companies")
    public void the_response_should_contain_a_list_of_companies() {

    }
    @Then("each company in the list should have a {string}, {string}, {string}, {string}, and {string}")
    public void each_company_in_the_list_should_have_a_and(String string, String string2, String string3, String string4, String string5) {

    }



}
