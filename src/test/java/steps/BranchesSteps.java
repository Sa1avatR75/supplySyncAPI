package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utilities.Config;
import utilities.SupplySyncToken;

public class BranchesSteps {
    private RequestSpecification request;
    private Response response;
    private ObjectMapper mapper = new ObjectMapper();
    private String token = SupplySyncToken.getToken();
    private final String baseURL = Config.getProperty("supplySyncAPIURL");


    @Given("sypplySync base URL")
    public void sypply_sync_base_url() {
        request = RestAssured.given().baseUri(baseURL);
    }
    @When("I provide VALID authorization token")
    public void i_provide_valid_authorization_token() {
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
            Assert.assertTrue(customResponse[i].getId() > 0);
        }
    }

}
