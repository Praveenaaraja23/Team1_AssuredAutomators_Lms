package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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

public class CreateBatchSteps {

	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;

	public CreateBatchSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}

	@Given("Admin sets Authorization to Bearer Token.")
	public void admin_sets_authorization_to_bearer_token() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates POST Request with valid data in request body for create batch")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_batch() {
		LoggerLoad.info("Admin sets post for create batch request");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATE_BATCH.getEndpoint();
	}

	@When("Admin sends HTTPS Request with data from row {string} for create batch")
	public void admin_sends_https_request_with_data_from_row_for_create_batch(String scenarioName)
			throws InvalidFormatException, IOException {
		LoggerLoad.info("Scenario Name: " + scenarioName);

		// try {
		List<Map<String, String>> batchData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Batch");
		for (Map<String, String> row : batchData) {
			if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

				Batch batch = new Batch();
				batch.setbatchDescription(row.get("BatchDescription"));
				batch.setbatchName(row.get("BatchName"));
				//batch.setbatchName(generateRandomString());
				batch.setbatchNoOfClasses(Integer.parseInt(row.get("NoOfClasses")));
				batch.setbatchStatus(row.get("BatchStatus"));
				if(scenarioName.equals("CreateBatchWithEmptyProgramId") || scenarioName.equals("CreateBatchWithInactiveProgramId"))
					batch.setbatchStatus(row.get("ProgramId"));
				else
					batch.setprogramId(GlobalContext.getProgramId(0));

				Response response = request.given().contentType("application/json").body(batch).log().body()
						.post(endPoint);

				scenarioContext.setResponse(response);
				scenarioContext.setRowData(row);

				LoggerLoad.info("Status Code: " + response.getStatusCode());
				if (response.getStatusCode() != 401 && response.getStatusCode() != 404) {
					LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
				}

				break;
			}
		}
//		} catch (Exception e) {
//			LoggerLoad.error("Exception :"+e.getMessage());
//		}

	}

	@Then("the response status should be equal to ExpectedStatus for create batch")
	public void the_response_status_should_be_equal_to_expected_status_for_create_batch() {
		Response response = scenarioContext.getResponse();
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = response.getStatusCode();

		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(response.getContentType(), expContentType);

		if (expStatusCode == 201 && actStatusCode == 201) {
			int batchId = Integer.parseInt(response.jsonPath().getString("batchId"));
			String batchName = response.jsonPath().getString("batchName");

			GlobalContext.addBatchId(batchId);
			GlobalContext.setBatchName(batchName);
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
			ResponseValidator.validateData(jsonPath.getString("batchName"), expRow.get("BatchName"));
			ResponseValidator.validateData(jsonPath.getString("batchDescription"), expRow.get("BatchDescription"));
			ResponseValidator.validateData(jsonPath.getString("batchNoOfClasses"), expRow.get("NoOfClasses"));
			ResponseValidator.validateData(jsonPath.getString("batchStatus"), expRow.get("BatchStatus"));
			ResponseValidator.validateData(jsonPath.getString("programId"), String.valueOf(GlobalContext.getProgramId(0)));
		}

	}

	// No auth
	@Given("Admin sets Authorization to No Auth, creates POST Request with valid data in request body for create batch")
	public void admin_sets_authorization_to_no_auth_creates_post_request_with_valid_data_in_request_body_for_create_batch() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATE_BATCH.getEndpoint();
		request = RestAssured.given();
	}

	@Given("Admin creates POST Request  with valid data in request body with invalid endpoint for create batch")
	public void admin_creates_post_request_with_valid_data_in_request_body_with_invalid_endpoint_for_create_batch() {
		LoggerLoad.info("Admin creates POST Request  with valid data in request body with invalid endpoint");
		endPoint = ConfigReader.getBaseUrl() + "1" + EndPoints.CREATE_BATCH.getEndpoint();
	}

	// This should be removed in final submission
	/*
	 * public static String generateRandomString() { String prefix = "Java"; String
	 * alphanumeric =
	 * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"; Random
	 * random = new Random();
	 * 
	 * // StringBuilder to append the initial "Java" StringBuilder stringBuilder =
	 * new StringBuilder(prefix);
	 * 
	 * // Generate remaining 6 characters for (int i = 0; i < 6; i++) { char
	 * randomChar = alphanumeric.charAt(random.nextInt(alphanumeric.length()));
	 * stringBuilder.append(randomChar); }
	 * 
	 * return stringBuilder.toString(); }
	 */
}
