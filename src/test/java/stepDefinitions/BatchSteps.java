package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Batch;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;

public class BatchSteps {

	private final ScenarioContext scenarioContext;
	private RequestSpecification request;

	public BatchSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}

	@Given("Admin sets Authorization to Bearer Token.")
	public void admin_sets_authorization_to_bearer_token() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates POST Request  with valid data in request body")
	public void admin_creates_post_request_with_valid_data_in_request_body() {
		LoggerLoad.info("Admin sets post request");
	}

	@When("Admin sends HTTPS Request with data from row {string}")
	public void admin_sends_https_request_with_data_from_row(String scenarioName)
			throws InvalidFormatException, IOException {
		LoggerLoad.info("Scenario Name: " + scenarioName);
		
		try {
			List<Map<String, String>> batchData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Batch");
			for (Map<String, String> row : batchData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					Batch batch = new Batch();
					batch.setbatchDescription(row.get("BatchDescription"));
					batch.setbatchName(row.get("BatchName"));
					batch.setbatchNoOfClasses(row.get("NoOfClasses"));
					batch.setbatchStatus(row.get("BatchStatus"));
					batch.setprogramId(row.get("ProgramId"));// To do get Program id from context

					String endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATE_BATCH.getEndpoint();
					Response response = request.given().contentType("application/json").body(batch).log().body()
							.post(endPoint);

					scenarioContext.setResponse(response);
					scenarioContext.setRowData(row);

					LoggerLoad.info("Status Code: " + response.getStatusCode());
					LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));

					break;
				}
			}
		} catch (Exception e) {
			LoggerLoad.error(e.getMessage());
		}

	}

	@Then("the response status should be equal to ExpectedStatus")
	public void the_response_status_should_be_equal_to_expected_status() {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();

		if (expStatusCode == 201 && actStatusCode == 201) {
			int batchId = Integer.parseInt(scenarioContext.getResponse().jsonPath().getString("batchId"));
			GlobalContext.addBatchId(batchId);
			LoggerLoad.info("batchId :" + batchId);
		}

		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);
	}

}
