package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.hu.Ha;
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

public class BranchesSteps {
    private RequestSpecification request;
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private String token = SupplySyncToken.getToken();
    private final String baseURL = Config.getProperty("supplySyncAPIURL");
    private CustomResponse customResponse;

    private CustomResponse[] customResponsesArray;

    private List<Integer> companyIDS = new ArrayList<>();
    private HashMap<String , Object > paramas = new HashMap<>();


    @Given("sypplySync base URL")
    public void sypply_sync_base_url() {
        request = RestAssured.given().baseUri(baseURL);
    }
    @When("I provide VALID authorization token for branches")
    public void i_provide_valid_authorization_token_for_branches() {
        request.auth().oauth2(token);
    }
    @When("I hit GET endpoint {string}")
    public void i_hit_get_endpoint(String endpoint) {
        response = request.get(endpoint);

    }
    @Then("I should receive a {int} OK status code")
    public void i_should_receive_a_ok_status_code(Integer expectedStatusCode) {
        Assert.assertEquals((int) expectedStatusCode, response.getStatusCode());
    }
    @Then("verify id of all branches is not NULL")
    public void verify_id_of_all_branches_is_not_null() throws JsonProcessingException {
        CustomResponse[] customResponse = mapper.readValue(response.asString(), CustomResponse[].class);
        for (int i = 0; i < customResponse.length; i++) {
            Assert.assertTrue(Integer.parseInt(customResponse[i].getId()) > 0);
        }
    }





    @When("I provide request body name {string}, email {string}, address {string}, phone number {string}, regionId {int}, companyId {int}")
    public void i_provide_request_body_name_email_address_phone_number_region_id_company_id(String name, String email, String address, String phone, Integer regionId, Integer companyID) {
        RequestBody requestBody = new RequestBody();
        requestBody.setName(name);
        requestBody.setEmail(email);
        requestBody.setAddress(address);
        requestBody.setPhoneNumber(phone);
        requestBody.setRegionId(regionId);
        requestBody.setCompanyId(companyID);

        request.contentType(ContentType.JSON).body(requestBody);

    }
    @When("I hit POST endpoint {string}")
    public void i_hit_post_endpoint(String endpoint) throws JsonProcessingException {
        response = request.post(endpoint);
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        response.prettyPrint();

    }
    @Then("the response should contains the name {string}")
    public void the_response_should_contains_the_name(String name) {
        Assert.assertEquals(name, customResponse.getName());

    }
    @Then("the response should contains the email {string}")
    public void the_response_should_contains_the_email(String email) {
        Assert.assertEquals(email, customResponse.getEmail());

    }
    @Then("the response should contains the address {string}")
    public void the_response_should_contains_the_address(String address) {
        Assert.assertEquals(address, customResponse.getAddress());

    }
    @Then("the response should contains the phone number {string}")
    public void the_response_should_contains_the_phone_number(String phone) {
        Assert.assertEquals(phone, customResponse.getPhoneNumber());

    }



    @When("I provide INVALID authorization token for branches")
    public void i_provide_invalid_authorization_token_for_branches() {
        request.auth().oauth2("INVALID token");
    }
    @Then("I should receive a {int} UNAUTHORIZED status code")
    public void i_should_receive_a_unauthorized_status_code(Integer statusCode) {
        Assert.assertEquals((int) statusCode, response.getStatusCode());

    }

    @Then("verify each branch is not blocked")
    public void verify_each_branch_is_not_blocked() throws JsonProcessingException {
        customResponsesArray = mapper.readValue(response.asString(), CustomResponse[].class);

        for (int i = 0; i < customResponsesArray.length; i++) {
            Assert.assertEquals(false, customResponsesArray[i].isBlock());
        }

    }

    @When("I hit a GET endpoint {string}")
    public void i_hit_a_get_endpoint(String endpoint) throws JsonProcessingException {
       response = request.get(endpoint);
//       customResponse = mapper.readValue(response.asString(), CustomResponse.class);
//        System.out.println(customResponse.getCompany().get(0).getId());

    }

    @Then("verify each company has branches")
    public void verify_each_company_has_branches()  {


    }


//    @Then("get id of each company")
//    public void get_id_of_each_company() throws JsonProcessingException {
//        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
//        for (int i = 0; i < customResponse.getCompany().size(); i++) {
//            companyIDS.add(Integer.valueOf(customResponse.getCompany().get(i).getId()));
//        }
//    }

    @Then("I git GET endpoint {string}")
    public void i_git_get_endpoint(String endpoint) throws JsonProcessingException {
        for (int i = 0; i < companyIDS.size(); i++) {
            response = request.param("companyId",companyIDS.get(i)).get(endpoint);
            Assert.assertEquals(200, response.getStatusCode());
            Assert.assertTrue(response.asString() != null);
            //            customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        }
    }

    int id;
    @Then("I get id of created branch")
    public void i_get_id_of_created_branch() {
        id = Integer.parseInt(customResponse.getId());
    }

    @Then("I hit DELETE endpoint {string}")
    public void i_hit_delete_endpoint(String endpoint) {
        response = request.delete(endpoint + id);
    }


    @When("I hit DELETE endpoint {string} with Branch id {int}")
    public void i_hit_delete_endpoint_with_branch_id(String endpoint, Integer int1) {
        System.out.println(endpoint + int1);
        response = request.delete(endpoint + int1);
        response.prettyPrint();
    }

}
