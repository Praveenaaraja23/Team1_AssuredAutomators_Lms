package stepDefinitions;

import java.util.List;
import java.util.Map;

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

public class UpdateBatchSteps {
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;

	public UpdateBatchSteps(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;

	}

	@Given("Admin sets Authorization to Bearer Token for update batch.")
	public void admin_sets_authorization_to_bearer_token_for_update_batch() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates PUT Request with valid BatchId and Data")
	public void admin_creates_put_request_with_valid_batch_id_and_data() {
		LoggerLoad.info("Admin sets post request for update batch");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_BATCH_BY_ID.getEndpoint();
	}

	@When("Admin sends PUT Request with data from row {string} for update batch")
	public void admin_sends_put_request_with_data_from_row_for_update_batch(String scenarioName) {
		LoggerLoad.info("Scenario Name: " + scenarioName);

		try {
			List<Map<String, String>> batchData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Batch");
			for (Map<String, String> row : batchData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

					Batch batch = new Batch();
					batch.setbatchDescription(row.get("BatchDescription"));
					batch.setbatchName(row.get("BatchName"));
					// batch.setbatchName(generateRandomString());
					batch.setbatchNoOfClasses(Integer.parseInt(row.get("NoOfClasses")));
					batch.setbatchStatus(row.get("BatchStatus"));
					batch.setprogramId(Integer.parseInt(row.get("ProgramId")));// To do get Program id from context

					Response response = request.given().contentType("application/json")
							.pathParam("batchId", GlobalContext.getBatchId(0)).body(batch).log().body().put(endPoint);

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

	@Then("the response status should be equal to ExpectedStatus for update batch")
	public void the_response_status_should_be_equal_to_expected_status_for_update_batch() {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();

		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);

	}

}
