package stepDefinitions;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.testng.Assert;

import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;

public class GetBatchesSteps {
	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;

	public GetBatchesSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for GET batch request")
	public void admin_sets_authorization_to_bearer_token_for_get_batch_request() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}	
	
	@Given("Admin creates GET Request for get batches")
	public void admin_creates_get_request_for_get_batches() {
		LoggerLoad.info("Admin sets GET request for get batches with valid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_ALL_BATCHES.getEndpoint();
	}

	@When("Admin sends HTTPS Request with endpoint for get batches")
	public void admin_sends_https_request_with_endpoint_for_get_batches() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} OK Status with response body for get batches.")
	public void admin_receives_ok_status_with_response_body_for_get_batches(Integer expStatusCode) {
		
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	    
	}
	
	// sending get all batches request with invalid end point
	@Given("Admin creates GET Request with invalid endpoint for get batches")
	public void admin_creates_get_request_with_invalid_endpoint_for_get_batches() {
		LoggerLoad.info("Admin sets GET request for get batches with in valid endpoint");
		 endPoint = ConfigReader.getBaseUrl()+"1" + EndPoints.GET_ALL_BATCHES.getEndpoint();
	}

	@When("Admin sends HTTPS Request with  invalid endpoint for get batches")
	public void admin_sends_https_request_with_invalid_endpoint_for_get_batches() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message Not Found for get batches")
	public void admin_receives_status_with_error_message_not_found_for_get_batches(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//No auth for get batches
	@Given("Admin sets no Authorization and creates GET Request for get batches")
	public void admin_sets_no_authorization_and_creates_get_request_for_get_batches() {
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_ALL_BATCHES.getEndpoint();
			request = RestAssured.given();
	}

	@When("Admin sends HTTPS Request with No auth, valid endpoint for get batches")
	public void admin_sends_https_request_with_no_auth_valid_endpoint_for_get_batches() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message Unauthorized for get batches")
	public void admin_receives_status_with_error_message_unauthorized_for_get_batches(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	// get batches with search string
	
	@Given("Admin creates GET Request for get batches with search string")
	public void admin_creates_get_request_for_get_batches_with_search_string() {
		LoggerLoad.info("Admin sets GET request for get batches with valid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_ALL_BATCHES.getEndpoint();
	}

	@When("Admin sends HTTPS Request with endpoint for get batches with search string {string}")
	public void admin_sends_https_request_with_endpoint_for_get_batches_with_search_string(String searchString) {	   
	    
	     searchString = URLEncoder.encode("Java", StandardCharsets.UTF_8);
	     Response response = request.given()
	    		 .contentType("application/json")
	    		 .queryParam("searchString", searchString)
	    		 .get(endPoint);
	    
	}

	@Then("Admin receives {int} OK Status with response body for get batches with search string {string}.")
	public void admin_receives_ok_status_with_response_body_for_get_batches_with_search_string(Integer expStatusCode, String expectedKeyword) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);

        // Extract batch names using JsonPath
        List<String> batchNames = scenarioContext.getResponse().jsonPath().getList("batchName");

        // Validate batch names contain the expected keyword        
        boolean allBatchesMatch = batchNames.stream().allMatch(name -> name.toLowerCase().contains(expectedKeyword.toLowerCase()));
        Assert.assertTrue(allBatchesMatch, "Not all batch names contain the keyword: " + expectedKeyword);
        System.out.println("All batch names contain the keyword: " + expectedKeyword);
    }
	




	//Get Batch by batch id
	@Given("Admin creates GET Request with valid Batch ID")
	public void admin_creates_get_request_with_valid_batch_id() {
		LoggerLoad.info("Admin sets GET request for batch id");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_BATCH_BY_ID.getEndpoint();
	}

	@When("Admin sends HTTPS Request with endpoint to get batch details")
	public void admin_sends_https_request_with_endpoint_to_get_batch_details() {
		Response response = request.given()
				.pathParam("batchId", GlobalContext.getBatchId(0))
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} OK Status with response body for batch.")
	public void admin_receives_ok_status_with_response_body_for_batch(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//Get Batch by batch name
	@Given("Admin creates GET Request with valid Batch Name")
	public void admin_creates_get_request_with_valid_batch_name() {
		LoggerLoad.info("Admin sets GET request for batch name");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_BATCH_BY_BATCHNAME.getEndpoint();
	}

	@When("Admin sends HTTPS Request with endpoint for get batch by batch name")
	public void admin_sends_https_request_with_endpoint_for_get_batch_by_batch_name() {		
		Response response = request.given()
				.pathParam("batchName", GlobalContext.getBatchName())
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} OK Status with batch response body for corresponding batch name.")
	public void admin_receives_ok_status_with_batch_response_body_for_corresponding_batch_name(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	// no auth for get batch by batch name
	@Given("Admin sets no Authorization and creates GET Request for get batch by batch name")
	public void admin_sets_no_authorization_and_creates_get_request_for_get_batch_by_batch_name() {
		LoggerLoad.info("Admin sets GET request for batch name");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_BATCH_BY_BATCHNAME.getEndpoint();
		 request = RestAssured.given();
		 
	}

	@When("Admin sends HTTPS Request with No auth, valid endpoint for get batch by batch name")
	public void admin_sends_https_request_with_no_auth_valid_endpoint_for_get_batch_by_batch_name() {
		
		Response response = request.given()
				.pathParam("batchName", GlobalContext.getBatchName())
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message Unauthorized for get batch by batch name.")
	public void admin_receives_status_with_error_message_unauthorized_for_get_batch_by_batch_name(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	//GET Request with invalid endpoint for get batch by batch name
	
	@Given("Admin creates GET Request with invalid endpoint for get batch by batch name")
	public void admin_creates_get_request_with_invalid_endpoint_for_get_batch_by_batch_name() {
		LoggerLoad.info("Admin sets GET request for get batches with in valid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + "1" + EndPoints.GET_BATCH_BY_BATCHNAME.getEndpoint();
	}

	@When("Admin sends HTTPS Request with  invalid endpoint for get batch by batch name")
	public void admin_sends_https_request_with_invalid_endpoint_for_get_batch_by_batch_name() {
		Response response = request.given()
				.pathParam("batchName", GlobalContext.getBatchName())
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message Not Found for get batch by batch name.")
	public void admin_receives_status_with_error_message_not_found_for_get_batch_by_batch_name(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	// Get Batch by program id
	
	@Given("Admin creates GET Request with valid Program Id")
	public void admin_creates_get_request_with_valid_program_id() {
		LoggerLoad.info("Admin sets GET request for batch name with Program Id");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_BATCH_BY_PROGRAMID.getEndpoint();
	}

	@When("Admin sends HTTPS Request with valid Program Id for GET batch by program id")
	public void admin_sends_https_request_with_valid_program_id_for_get_batch_by_program_id() {
		Response response = request.given()
				.pathParam("batchName", GlobalContext.getProgramId(0))
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+response.jsonPath().get());
	}

	@Then("Admin receives {int} OK Status with batch response body for corresponding program.")
	public void admin_receives_ok_status_with_batch_response_body_for_corresponding_program(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}






}
