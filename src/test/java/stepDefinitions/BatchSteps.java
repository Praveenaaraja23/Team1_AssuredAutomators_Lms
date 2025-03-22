package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import context.ContextKey;
import context.TestContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Batch;
import utilities.BatchDataMapper;
import utilities.ConfigReader;
import utilities.ExcelReader;

public class BatchSteps {
	
	private TestContext context;

	public BatchSteps(TestContext testContext) throws IOException
	{
		this.context= testContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token.")
	public void admin_sets_authorization_to_bearer_token() {
		String token = context.getContext(ContextKey.TOKEN.name()).toString();
		RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates POST Request  with valid data in request body")
	public void admin_creates_post_request_with_valid_data_in_request_body() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("Admin sends HTTPS Request with data from row {string}")
	public void admin_sends_https_request_with_data_from_row(String string) throws InvalidFormatException, IOException {
		List<Map<String, String>> batchData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Batch");
		 
		for(Map<String, String> row: batchData)
		{
			Batch batch = new Batch();			
			batch.setbatchDescription(row.get("BatchDescription"));
			batch.setbatchName(row.get("BatchName"));
			batch.setbatchNoOfClasses(row.get("NoOfClasses"));
			batch.setbatchStatus(row.get("BatchStatus"));
			batch.setprogramId(row.get("ProgramId"));// To do get Program id from context
			
			Response response = RestAssured.given()
	                .contentType("application/json")
	                .body(batch)
	                .log().body()
	                .post(ConfigReader.getProperty("baseUrl"+"/batches"));
			
		}
			
	}

	@Then("the response status should be equal to ExpectedStatus")
	public void the_response_status_should_be_equal_to_expected_status() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

}
