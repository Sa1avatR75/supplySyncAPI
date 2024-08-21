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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

        private  static Logger logger = LogManager.getLogger(RegionSteps.class);
    @Given("base url for SupplySync backend")
    public void base_url_for_supply_sync_backend() {
        request = RestAssured.given().baseUri(Config.getProperty("supplySyncAPIURL"));
        logger.info("Base url is given");
    }
    @When("I provide valid authorization token")
    public void i_provide_valid_authorization_token() {
        request = request.auth().oauth2(SupplySyncToken.getToken());
        logger.info("Authorization token is given");
    }
    @When("I provide valid id and region as a request body")
    public void i_provide_valid_id_and_region_as_a_request_body() {
        requestBody.setId(12312412);
        requestBody.setRegion(faker.address().city());
        request = request.contentType(ContentType.JSON).body(requestBody);
        logger.info("Request body is added");
    }
    @When("I hit POST endpoint {string}")
    public void i_hit_post_endpoint(String endPoint) throws JsonProcessingException {
        response = request.post(endPoint);
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        response.prettyPrint();
        regionToCheck = customResponse.getRegion();
        idToDelete = Integer.valueOf(customResponse.getId());
        logger.info("Post endpoint is hit");


    }

    @Then("I verify the status code is {int}")
    public void i_verify_the_status_code_is(Integer expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();

        logger.info("Verifying status code. Expected: {}, Actual: {}", expectedStatusCode, actualStatusCode);

        try {
            Assert.assertEquals((int) expectedStatusCode, actualStatusCode);
            logger.info("Status code verification passed.");
        } catch (AssertionError e) {
            logger.error("Status code verification failed. Expected: {}, but got: {}", expectedStatusCode, actualStatusCode);
            throw e;
        }
    }

    @When("I provide id {int} and updated {string}")
    public void i_provide_id_and_updated(Integer id, String updatedRegion) {
        updatedRegion = faker.address().city();
        requestBody.setId(Integer.valueOf(customResponse.getId()));
        requestBody.setRegion(updatedRegion);
        request = request.contentType(ContentType.JSON).body(requestBody);
        logger.info("Request body is given");

    }
    @When("I hit PUT endpoint {string} to create region")
    public void i_hit_put_endpoint_to_create_region(String endPoint) throws JsonProcessingException {
        response = request.put(endPoint);
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        logger.info("PUT endpoint is hit");


    }

    @Then("I get id of created region")
    public void i_get_id_of_created_region() throws JsonProcessingException {
        customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        logger.info("The id of created region is retrieved");

    }

    @When("I hit DELETE endpoint {string} with valid id")
    public void i_hit_delete_endpoint_with_valid_id(String endPoint)  {
        response = request.delete(endPoint + customResponse.getId());
        logger.info("DELETE endpoint is hit");

    }
    @Then("I verify message contains {string}")
    public void i_verify_message_contains(String expectedMessage) {
        String actualMessage = response.getBody().asString();
        logger.info("Verifying message. Expected: {}, Actual {}", expectedMessage, actualMessage );
        try {
            Assert.assertTrue(response.getBody().asString().contains(expectedMessage));
            logger.info("Message has been verified successfully");
        } catch (AssertionError e) {
            logger.info("Verification failed. Expected: {}, Actual {}", expectedMessage, actualMessage);
            throw e;
        }


    }

    @Then("I hit GET endpoint {string} to get list of regions")
    public void i_hit_get_endpoint_to_get_list_of_regions(String endPoint) throws JsonProcessingException {
        response = request.get(endPoint);
        customResponses = mapper.readValue(response.asString(), CustomResponse[].class);
        logger.info("Array of responses has been mapped to custom response");
    }

    @Then("I verify that regions list contains new created region")
    public void i_verify_that_regions_list_contains_new_created_region() throws JsonProcessingException {
        boolean doesContain = false;
        logger.info("Verifying if the regions list contains the newly created region: {}", regionToCheck);
        for(int i = 0; i < customResponses.length; i++) {
            if(customResponses[i].getRegion() != null  && customResponses[i].getRegion().equals(regionToCheck)) {
                doesContain = true;
                break;
            }
        }

        if (doesContain) {
            logger.info("The regions list contains the newly created region: {}", regionToCheck);
        } else {
            logger.error("The regions list does NOT contain the newly created region: {}", regionToCheck);
        }

        Assert.assertTrue("The regions list should contain the newly created region.", doesContain);
    }

    }




}

