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




public class GetUserSteps{
	
	RequestSpecification httpRequest;
	Response res;	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;
	
	public GetUserSteps(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for Get User.")
	public void admin_sets_authorization_to_bearer_token_for_get_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

	//Get all Users
	@Given("Admin creates GET Request with valid user data in request body")
	public void admin_creates_get_request_with_valid_user_data_in_request_body() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_ALL_USERS.getEndpoint();
		System.out.println("Admin create Get request for All Users");
	}
	@When("Admin sends GET Request with endpoint")
	public void admin_sends_get_request_with_endpoint() {
		Response response = request.given().contentType("application/json").get(endPoint);
		
		scenarioContext.setResponse(response);
		LoggerLoad.info("Status Code: " + response.getStatusCode());
		LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
	}

	
	//Get user by user Id
	@Given("User creates GET Request with valid User Id")
	public void user_creates_get_request_with_valid_user_id() {
		LoggerLoad.info("Admin sets GET request for user id");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_USER_INFORMATION_BY_USERID.getEndpoint();
		Response response = request.given()
				.pathParam("userId", GlobalContext.getUserId(0))
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
		
	}

	@When("Admin sends GET Request with valid endpoint to get User details")
	public void admin_sends_get_request_with_valid_endpoint_to_get_user_details() {
	System.out.println("Admin get user information");
	}

	@Given("User creates GET Request with invalid User Id")
	public void user_creates_get_request_with_invalid_user_id() {
		LoggerLoad.info("Admin sets GET request for invalid user id");
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_USER_INFORMATION_BY_USERID.getEndpoint();
		Response response = request.given()
				.pathParam("userId", "ABC#")
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
		
	}

	@Then("Admin receives {int} Not Found Status with response body.")
	public void admin_receives_not_found_status_with_response_body(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	
	
	//Get all Staff 
	@Given("User creates GET Request for the LMS API All Staff endpoint")
	public void user_creates_get_request_for_the_lms_api_all_staff_endpoint() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_USER_INFORMATION_BY_STAFFID.getEndpoint();
		System.out.println("Admin creates Get request for All Staff");

	}
	
	@When("Admin sends GET Request with staff endpoint")
	public void admin_sends_get_request_with_staff_endpoint() {
		Response response = request.given()
				.pathParam("staffId", GlobalContext.getstaffId(0))
				.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
			
	}
	
	// Get all Roles
	@Given("User creates GET Request for the LMS API All Roles endpoint")
	public void user_creates_get_request_for_the_lms_api_all_roles_endpoint() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_ALL_ROLES.getEndpoint();
		System.out.println("Admin creates Get request for All Staff");
	}

	@When("Admin sends GET Request with all Roles endpoint")
	public void admin_sends_get_request_with_all_roles_endpoint() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("Status Code: " + response.getStatusCode());
		LoggerLoad.info("Status Message: " + response.jsonPath().getString("message"));
	}
	
	//Get all Users, all Staff, all Roles
	@Then("Admin receives {int} OK Status with response body.")
	public void admin_receives_ok_status_with_response_body(Integer expStatusCode) {
		int statusCode= scenarioContext.getResponse().statusCode();
		System.out.println(statusCode);
		LoggerLoad.info("StatusCode : "+statusCode);
		ResponseValidator.validateStatusCode(statusCode, expStatusCode);
	}
	
}
	