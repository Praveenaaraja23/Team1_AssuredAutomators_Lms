package stepDefinitions;

import java.util.List;
import java.util.Map;

import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
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
	//Valid batchId for update batch
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
															
					if(scenarioName.equals("UpdateBatchWithDeletedProgramID"))
						batch.setprogramId(GlobalContext.getProgramId(1));
					else
						batch.setprogramId(GlobalContext.getProgramId(0));					
					
					int batchId;
					if(scenarioName.equalsIgnoreCase("UpdateBatchWithInvalidBatchId"))
						batchId = 0;
					else if(scenarioName.equalsIgnoreCase("UpdateBatchWithDeletedBatchId"))
						batchId = GlobalContext.getBatchId(1);
					else
						batchId = GlobalContext.getBatchId(0);
					
					LoggerLoad.info("batchId :" + batchId);
					
					Response response = request.given().contentType("application/json")
							.pathParam("batchId", batchId).body(batch).log().body().put(endPoint);

					scenarioContext.setResponse(response);
					scenarioContext.setRowData(row);

					LoggerLoad.info("Status Code: " + response.getStatusCode());
					if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
						LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
					}

					break;
				}
			}
		} catch (Exception e) {
			LoggerLoad.error(e.getMessage());
		}
	}

	@Then("the response status should be equal to ExpectedStatus for update batch")
	public void the_response_status_should_be_equal_to_expected_status_for_update_batch() {
		Response response = scenarioContext.getResponse();
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = response.getStatusCode();

		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(response.getContentType(), expContentType);

		if (expStatusCode == 201 && actStatusCode == 201) {
			int batchId = Integer.parseInt(response.jsonPath().getString("batchId"));
			String batchName = response.jsonPath().getString("batchName");
			
			LoggerLoad.info("batchId :" + batchId);
			LoggerLoad.info("batchName :" + batchName);

			JsonPath jsonPath = response.jsonPath();
			Map<String, String> expRow = scenarioContext.getRowData();
			// Validate schema
			ResponseValidator.validateJsonSchema(response, "Batch_schema.json");

			// Validate Data type
			ResponseValidator.validateDataType(response, "batchId", Integer.class);
			ResponseValidator.validateDataType(response, "batchName", String.class);
			// ResponseValidator.validateDataType(response, "batchDescription", String.class);
			ResponseValidator.validateDataType(response, "batchStatus", String.class);
			ResponseValidator.validateDataType(response, "programName", String.class);
			ResponseValidator.validateDataType(response, "batchNoOfClasses", Integer.class);
			ResponseValidator.validateDataType(response, "programId", Integer.class);

			// Validate Data
			ResponseValidator.validateData(jsonPath.getString("batchId"), String.valueOf(GlobalContext.getBatchId(0)));
			ResponseValidator.validateData(jsonPath.getString("batchName"), expRow.get("BatchName"));
			ResponseValidator.validateData(jsonPath.getString("batchDescription"), expRow.get("BatchDescription"));
			ResponseValidator.validateData(jsonPath.getString("batchNoOfClasses"), expRow.get("NoOfClasses"));
			ResponseValidator.validateData(jsonPath.getString("batchStatus"), expRow.get("BatchStatus"));
			ResponseValidator.validateData(jsonPath.getString("programId"), String.valueOf(GlobalContext.getProgramId(0)));
		}

	}
	//No Auth for update batch
	@Given("Admin sets Authorization to No Auth for updating batch by batchId")
	public void admin_sets_authorization_to_no_auth_for_updating_batch_by_batch_id() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_BATCH_BY_ID.getEndpoint();
		request = RestAssured.given();
	}
	
	// update batch with invalid batchId
	@Given("Admin creates PUT Request with invalid BatchId and Data")
	public void admin_creates_put_request_with_invalid_batch_id_and_data() {
		LoggerLoad.info("Admin creates POST Request  with valid data in request body with invalid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_BATCH_BY_ID.getEndpoint();
	}

//	@When("Admin creates PUT Request with invalid BatchId and valid Data")
//	public void admin_creates_put_request_with_invalid_batch_id_and_valid_data() {
//		Response response = request.given()
//				.pathParam("id", GlobalContext.getBatchId(1)+ "1")
//				.contentType("application/json").put(endPoint);
//		scenarioContext.setResponse(response);
//		LoggerLoad.info("response : "+ response.asString());
//	}

	@Then("Admin receives {int} Not Found Status with message and boolean success details for update batch")
	public void admin_receives_not_found_status_with_message_and_boolean_success_details_for_update_batch(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	@Then("Admin receives {int} Bad Request Status with message and boolean success details for updating batch")
	public void admin_receives_bad_request_status_with_message_and_boolean_success_details_for_updating_batch(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	

}
