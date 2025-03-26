package stepDefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import io.restassured.path.json.JsonPath;




public class CreateUserSteps{
	
	RequestSpecification httpRequest;
	Response res;	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;
	
	public CreateUserSteps(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for create user.")
	public void admin_sets_authorization_to_bearer_token_for_create_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	@Given("Admin creates POST Request with valid user data in request body")
	public void admin_creates_post_request_with_valid_user_data_in_request_body() {
		LoggerLoad.info("Admin sets User post request for create User");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATING_USER_WITH_ROLE.getEndpoint();
	}

	@When("Admin sends HTTPS Request with user data from row {string}")
	public void admin_sends_https_request_with_user_data_from_row(String scenarioName) {
	  
         LoggerLoad.info("Scenario Name: " + scenarioName);
		
		try {
			List<Map<String, String>> userData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");
			for (Map<String, String> row : userData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					User user = new User();
					user.setUserFirstName(row.get("userFirstName"));
					user.setUserLastName(row.get("userLastName"));
					user.setUserMiddleName(row.get("userMiddleName"));
					user.setUserPhoneNumber(row.get("userPhoneNumber"));
					user.setUserLocation(row.get("userLocation"));
					user.setUserTimeZone(row.get("userTimeZone"));
					user.setUserLinkedinUrl(row.get("userLinkedinUrl"));
					user.setUserEduUg(row.get("userEduUg"));
					user.setUserEduPg(row.get("userEduPg"));
					user.setUserComments(row.get("userComments"));
					user.setUserVisaStatus(row.get("userVisaStatus"));
					
					List<UserRoleMap> userRoleMaps = new ArrayList<UserRoleMap>();
					String[] roleIds=row.get("roleId").split(" , ");
					String[] userRoleStatus=row.get("userRolStatus").split(" , ");
					 for (int i=0; i<roleIds.length; i++)
					 {
						 UserRoleMap roleMap = new UserRoleMap();
						 roleMap.setRoleId(roleIds[i]);
						 roleMap.setuserRoleStatus(userRoleStatus[0]);
						 userRoleMaps.add(roleMap);
					 }
					 user.setUserRoleMaps(userRoleMaps);
					 
					 UserLogin userLogin= new UserLogin();
					 userLogin.setloginStatus(row.get("loginStatus"));
					 userLogin.setuserLoginEmail(row.get("userLoginEmail"));
					 user.setUserLogin(userLogin);
					

					
					Response response = request.given().contentType("application/json").body(user).log().body()
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

	@Then("the user api response status should be equal to ExpectedStatus")
	public void the_user_api_response_status_should_be_equal_to_expected_status() {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();

		if (expStatusCode == 201 && actStatusCode == 201) {
			int userId = Integer.parseInt(scenarioContext.getResponse().jsonPath().getString("userId"));
			GlobalContext.addUserId(userId);
			LoggerLoad.info("userId :" + userId);
			
			JsonPath jsonPath = scenarioContext.getResponse().jsonPath();
			  Map<String, String> expRow = scenarioContext.getRowData();
			  
			// Validate Data type
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userComments", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userEduPg", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userEduUg", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userFirstName", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userId", Integer.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userLastName", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userLinkedinUrl", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userLocation", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userMiddleName", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userPhoneNumber", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userTimeZone", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userVisaStatus", String.class);
			ResponseValidator.validateDataType(scenarioContext.getResponse(), "userLoginEmail", String.class);

			// Validate Data
			ResponseValidator.validateData(jsonPath.getString("userComments"), expRow.get("userComments"));
			ResponseValidator.validateData(jsonPath.getString("userEduPg"), expRow.get("userEduPg"));
			ResponseValidator.validateData(jsonPath.getString("userEduUg"), expRow.get("userEduUg"));
			ResponseValidator.validateData(jsonPath.getString("userFirstName"), expRow.get("userFirstName"));
			ResponseValidator.validateData(jsonPath.getString("userLastName"), expRow.get("userLastName"));
			ResponseValidator.validateData(jsonPath.getString("userLinkedinUrl"), expRow.get("userLinkedinUrl"));
			ResponseValidator.validateData(jsonPath.getString("userLocation"), expRow.get("userLocation"));
			ResponseValidator.validateData(jsonPath.getString("userMiddleName"), expRow.get("userMiddleName"));
			ResponseValidator.validateData(jsonPath.getString("userPhoneNumber"), expRow.get("userPhoneNumber"));
			ResponseValidator.validateData(jsonPath.getString("userTimeZone"), expRow.get("userTimeZone"));
			ResponseValidator.validateData(jsonPath.getString("userVisaStatus"), expRow.get("userVisaStatus"));
			ResponseValidator.validateData(jsonPath.getString("userLoginEmail"), expRow.get("userLoginEmail"));
		    
		}

		
	}


	
}
	