package stepDefinitions;

import java.io.IOException;

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

public class DeleteBatchSteps {
	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;

	public DeleteBatchSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for DELETE batch request")
	public void admin_sets_authorization_to_bearer_token_for_delete_batch_request() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}
	// Admin sets GET request for get batches with valid endpoint,  valid BatchId"

	@Given("Admin creates a DELETE request with a valid BatchId.")
	public void admin_creates_a_delete_request_with_a_valid_batch_id() {
		LoggerLoad.info("Admin sets GET request for get batches with valid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.DELETE_BATCH_ID. getEndpoint();
	}

	@When("Admin sends  HTTPS request to the endpoint for deleting a batch by BatchId")
	public void admin_sends_https_request_to_the_endpoint_for_deleting_a_batch_by_batch_id() {
		Response response = request.given()
				.pathParam("id", GlobalContext.getBatchId(1))
				.contentType("application/json").delete(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives a {int} OK status with a message confirming the deletion of the batch by BatchId.")
	public void admin_receives_a_ok_status_with_a_message_confirming_the_deletion_of_the_batch_by_batch_id(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	// Admin creates DELETE Request with valid BatchId with invalid endpoint

	@Given("Admin creates DELETE Request with valid BatchId with invalid endpoint")
	public void admin_creates_delete_request_with_valid_batch_id_with_invalid_endpoint() {
		LoggerLoad.info("Admin creates POST Request  with valid data in request body with invalid endpoint");
		 endPoint = ConfigReader.getBaseUrl()+"1" + EndPoints.DELETE_BATCH_ID.getEndpoint();
	}

	@When("Admin sends HTTPS Request  with invalid endpoint for deleting a batch by BatchId")
	public void admin_sends_https_request_with_invalid_endpoint_for_deleting_a_batch_by_batch_id() {
		Response response = request.given()
				.pathParam("id", GlobalContext.getBatchId(1))
				.contentType("application/json").delete(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} Not Found with Message and boolean success details for the deletion of the batch by BatchId.")
	public void admin_receives_not_found_with_message_and_boolean_success_details_for_the_deletion_of_the_batch_by_batch_id(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	// Admin creates DELETE Request with invalid BatchId
	
	@Given("Admin creates DELETE Request with invalid BatchId")
	public void admin_creates_delete_request_with_invalid_batch_id() {
		LoggerLoad.info("Admin creates POST Request  with valid data in request body with invalid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.DELETE_BATCH_ID.getEndpoint();
	}

	@When("Admin sends HTTPS Request  with invalid BatchId for deleting a batch by BatchId")
	public void admin_sends_https_request_with_invalid_batch_id_for_deleting_a_batch_by_batch_id() {
		Response response = request.given()
				.pathParam("id", GlobalContext.getBatchId(1)+ "1")
				.contentType("application/json").delete(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}
	// Admin creates DELETE Request  without authorization 
	
	@Given("Admin creates DELETE Request  without authorization for the deletion of the batch by BatchId")
	public void admin_creates_delete_request_without_authorization_for_the_deletion_of_the_batch_by_batch_id() {
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.DELETE_BATCH_ID.getEndpoint();
			request = RestAssured.given();
	}

	@Then("Admin receives {int} Unauthorized Status for the deletion of the batch by BatchId.")
	public void admin_receives_unauthorized_status_for_the_deletion_of_the_batch_by_batch_id(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}




}
