package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import models.UserLogin;
import models.UserRoleMap;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;

public class UserSteps {

	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;

	public UserSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}

	@Given("Admin sets Authorization to Bearer Token to create user.")
	public void admin_sets_authorization_to_bearer_token_to_create_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates POST Request  with valid data in request body for create user")
	public void admin_creates_post_request_with_valid_data_in_request_body_for_create_user() {
		LoggerLoad.info("Admin sets post request");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATE_USER.getEndpoint();
	}

	@When("Admin sends HTTPS Request with data from row {string} for create user")
	public void admin_sends_https_request_with_data_from_row_for_create_user(String scenarioName) {
		LoggerLoad.info("Scenario Name: " + scenarioName);

		try {
			List<Map<String, String>> userdata = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");
			for (Map<String, String> row : userdata) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {

					User user = new User();
					// Set simple fields
			        user.setuserComments(row.get("userComments"));
			        user.setuserEduPg(row.get("userEduPg"));
			        user.setuserEduUg(row.get("userEduUg"));
			        user.setuserFirstName(row.get("userFirstName"));
			        user.setuserLastName(row.get("userLastName"));
			        user.setuserLinkedinUrl(row.get("userLinkedinUrl"));
			        user.setuserLocation(row.get("userLocation"));
			        user.setuserMiddleName(row.get("userMiddleName"));
			        user.setuserPhoneNumber(row.get("userPhoneNumber"));
			        user.setuserTimeZone(row.get("userTimeZone"));
			        user.setuserVisaStatus(row.get("userVisaStatus"));
			        
			        // Set UserRoleMaps (assuming it can have multiple roles)
			        List<UserRoleMap> userRoleMaps = new ArrayList<>();
			        String[] roleIds = row.get("roleId").split(",");
			        String[] roleStatuses = row.get("userRoleStatus").split(",");

			        for (int i = 0; i < roleIds.length; i++) {
			            UserRoleMap roleMap = new UserRoleMap();
			            roleMap.setroleId(roleIds[i]);
			            roleMap.setuserRoleStatus(roleStatuses[i]);
			            userRoleMaps.add(roleMap);
			        }
			        user.setuserRoleMaps(userRoleMaps);

			        // Set UserLogin
			        UserLogin userLogin = new UserLogin();
			        userLogin.setloginStatus(row.get("loginStatus"));
			        userLogin.setuserLoginEmail(row.get("userLoginEmail"));
			        user.setuserLogin(userLogin);

					Response response = request.given().contentType("application/json").body(user).log().body()
							.post(endPoint);

					scenarioContext.setResponse(response);
					scenarioContext.setRowData(row);

					LoggerLoad.info("Status Code: " + response.getStatusCode());
					if(response.getStatusCode() != 401 && response.getStatusCode() != 404) {
						LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
					}

					break;
				}
			}
		} catch (Exception e) {
			LoggerLoad.error(e.getMessage());
		}
	}

	@Then("the response status should be equal to ExpectedStatus for create user")
	public void the_response_status_should_be_equal_to_expected_status_for_create_user() {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		
		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);
	}
}
