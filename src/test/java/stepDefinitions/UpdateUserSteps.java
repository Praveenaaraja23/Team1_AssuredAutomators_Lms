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




public class UpdateUserSteps{
	
	RequestSpecification httpRequest;
	Response res;	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;
	
	public UpdateUserSteps(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for update user.")
	public void admin_sets_authorization_to_bearer_token_for_update_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	//Update User by UserId
	@Given("Admin update PUT Request with valid user data in request body")
	public void admin_update_put_request_with_valid_user_data_in_request_body() {
		LoggerLoad.info("Admin sets User PUT request for Update User by UserId");
	    endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_USER.getEndpoint();
		
	}

	@When("Admin sends Put HTTPS Request with user data from row {string}")
	public void admin_sends_put_https_request_with_user_data_from_row(String scenarioName) {
		LoggerLoad.info("Admin sets User PUT request for Update User by UserId");
        LoggerLoad.info("Scenario Name: " + scenarioName);
		
		try {
			List<Map<String, String>> userData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");
			for (Map<String, String> row : userData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					User user = new User();
					String userId = String.valueOf(GlobalContext.getUserId(0));
					
					user.setuserid(userId);
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
					

					String endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_USER.getEndpoint();
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

	@Then("the user api response status should be equals to Expected Status")
	public void the_user_api_response_status_should_be_equals_to_expected_status() {
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();

		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);

	}
	
	
    //Update User Roll Status by UserId
   @Given("User update PUT Request with invalid userId and  the LMS API endpoint")
    public void user_update_put_request_with_invalid_user_id_and_the_lms_api_endpoint() {
	   LoggerLoad.info("Admin sets User PUT request for Roll Status by UserId");
	   endPoint = ConfigReader.getBaseUrl() + EndPoints.UPDATE_USER_ROLEID.getEndpoint();
}
   /*
   @When("User sends PUT HTTPS Request and  request Body with valid mandatory fields")
    public void user_sends_put_https_request_and_request_body_with_valid_mandatory_fields(String scenarioName) {
	   LoggerLoad.info("Admin sets User PUT request for Roll Status by UserId");
       LoggerLoad.info("Scenario Name: " + scenarioName);
		
       try {
			List<Map<String, String>> userData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "User");
			for (Map<String, String> row : userData) {
				if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
					
					User user = new User();
					String userId = String.valueOf(GlobalContext.getUserId(0));
					user.setuserid(userId);
					
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

   @Then("User receives {int} Bad Request Status with message and boolean success details")
   public void user_receives_bad_request_status_with_message_and_boolean_success_details(Integer int1) {
	   int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();

		if (expStatusCode == 201 && actStatusCode == 201) {
			int userId = Integer.parseInt(scenarioContext.getResponse().jsonPath().getString("userId"));
			GlobalContext.addUserId(userId);
			LoggerLoad.info("userId :" + userId);
			
			JsonPath jsonPath = scenarioContext.getResponse().jsonPath();
			  Map<String, String> expRow = scenarioContext.getRowData();
			  
			// Validate Data type
				ResponseValidator.validateDataType(scenarioContext.getResponse(), "roleId", String.class);
				ResponseValidator.validateDataType(scenarioContext.getResponse(), "userRoleStatus", String.class);
				
			// Validate Data
				ResponseValidator.validateData(jsonPath.getString("roleId"), expRow.get("roleId"));
				ResponseValidator.validateData(jsonPath.getString("userRoleStatus"), expRow.get("userRoleStatus"));
	
		
		}
}
*/


	
}
	