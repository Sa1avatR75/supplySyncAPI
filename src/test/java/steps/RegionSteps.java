package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

public class RegionSteps {
        RequestSpecification request;
        RequestBody requestBody = new RequestBody();
        CustomResponse customResponse = new CustomResponse();
        ObjectMapper mapper = new ObjectMapper();
        Response response;
        Faker faker = new Faker();
        CustomResponse[] customResponses;
        String regionToCheck;
        Integer idToDelete;



    @Given("base url for SupplySync backend")
    public void base_url_for_supply_sync_backend() {
        request = RestAssured.given().baseUri(Config.getProperty("supplySyncAPIURL"));
    }
    @When("I provide valid authorization token")
    public void i_provide_valid_authorization_token() {
        request = request.auth().oauth2(SupplySyncToken.getToken());
    }
    @When("I provide valid id and region as a request body")
    public void i_provide_valid_id_and_region_as_a_request_body() {
        requestBody.setId(12312412);
        requestBody.setRegion(faker.address().city());
        request = request.contentType(ContentType.JSON).body(requestBody);
    }
    @When("I hit POST endpoint {string}")
    public void i_hit_post_endpoint(String endPoint) throws JsonProcessingException {
        response = request.post(endPoint);
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        response.prettyPrint();
        regionToCheck = customResponse.getRegion();
        idToDelete = Integer.valueOf(customResponse.getId());

    }

    @Then("I verify the status code is {int}")
    public void i_verify_the_status_code_is(Integer int1) {
        Assert.assertEquals((int) int1, response.getStatusCode());
    }

    @When("I provide id {int} and updated {string}")
    public void i_provide_id_and_updated(Integer id, String updatedRegion) {
        updatedRegion = faker.address().city();
        requestBody.setId(Integer.valueOf(customResponse.getId()));
        requestBody.setRegion(updatedRegion);
        request = request.contentType(ContentType.JSON).body(requestBody);

    }
    @When("I hit PUT endpoint {string}")
    public void i_hit_put_endpoint(String endPoint) throws JsonProcessingException {
        response = request.put(endPoint);
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        response.prettyPrint();

    }

    @Then("I get id of created region")
    public void i_get_id_of_created_region() throws JsonProcessingException {
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);

    }

    @When("I hit DELETE endpoint {string} with valid id")
    public void i_hit_delete_endpoint_with_valid_id(String endPoint)  {

        response = request.delete(endPoint + customResponse.getId());


    }
    @Then("I verify message contains {string}")
    public void i_verify_message_contains(String message) {
        Assert.assertTrue(response.getBody().asString().contains(message));
        System.out.println(response.getBody().asString());
    }

    @Then("I hit GET endpoint {string} to get list of regions")
    public void i_hit_get_endpoint_to_get_list_of_regions(String endPoint) throws JsonProcessingException {
        response = request.get(endPoint);
        customResponses = mapper.readValue(response.asString(), CustomResponse[].class);
        System.out.println(customResponses[10].getRegion());
    }

    @Then("I verify that regions list contains new created region")
    public void i_verify_that_regions_list_contains_new_created_region() throws JsonProcessingException {
        boolean doesContain = false;

        for(int i = 0; i < customResponses.length; i++) {
            if(customResponses[i].getRegion() != null  && customResponses[i].getRegion().equals(regionToCheck)) {
                doesContain = true;
            }
        }

        Assert.assertTrue(doesContain);
    }


}
