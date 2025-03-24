package stepDefinitions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Login;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;
import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;

public class LoginSteps {

	private final ScenarioContext scenarioContext;
	String endPoint;

	public LoginSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}

	// Login-with Authorization
	@Given("Admin creates request with valid credentials")
	public void admin_creates_request_with_valid_credentials() {
		LoggerLoad.info("Admin sets HTTP request");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.USER_SIGN_IN.getEndpoint();

	}

	@When("Admin calls Post Https method  with valid endpoint with data from row {string}")
	public void admin_calls_post_https_method_with_valid_endpoint_with_data_from_row(String scenarioName) {
		LoggerLoad.info("Scenario Name: " + scenarioName);

		try {
			List<Map<String, String>> loginData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Login");
			for (Map<String, String> row : loginData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					Login login = new Login();
					login.setuserLoginEmailId(row.get("userLoginEmailId"));
					login.setpassword(row.get("password"));
					Response response = RestAssured.given().contentType("application/json").body(login).log().body()
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

	@Then("Admin receives {int} created with auto generated token")
	public void admin_receives_created_with_auto_generated_token(Integer int1) {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		
		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);
		
		if (expStatusCode == 200 && actStatusCode == 200) {
			String token = scenarioContext.getResponse().jsonPath().getString("token");
			GlobalContext.setToken(token);
			LoggerLoad.info("token :" + token);

			

		}
	}

}
