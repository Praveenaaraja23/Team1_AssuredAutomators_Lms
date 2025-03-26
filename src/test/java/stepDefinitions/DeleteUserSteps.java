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




public class DeleteUserSteps{
	
	RequestSpecification httpRequest;
	Response res;	
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;
	
	public DeleteUserSteps(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	
	@Given("Admin sets Authorization to Bearer Token for delete user.")
	public void admin_sets_authorization_to_bearer_token_for_delete_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}
	
//Delete User by Valid User Id
@Given("Admin creates Delete Request with valid user id in request body")
public void admin_creates_delete_request_with_valid_user_id_in_request_body() {
	LoggerLoad.info("Admin sets Delete request by User id");
	 endPoint = ConfigReader.getBaseUrl() + EndPoints.DELETE_USER.getEndpoint();
}

@When("Admin sends Delete Request with valid endpoint")
public void admin_sends_delete_request_with_valid_endpoint() {
	Response response = request.given()
			.pathParam("userId", GlobalContext.getUserId(0))
			.contentType("application/json").get(endPoint);
	scenarioContext.setResponse(response);
	LoggerLoad.info("response : "+ response.asString());
	
}

@Then("Admin receives {int} OK  Status with delete Id response body.")
public void admin_receives_ok_status_with_delete_id_response_body(Integer expStatusCode) {
	int actStatusCode = scenarioContext.getResponse().getStatusCode();
	LoggerLoad.info("actStatusCode : "+actStatusCode);
	ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
}

//Delete User by invalid User Id
@Given("Admin creates Delete Request with invalid user id in request body")
public void admin_creates_delete_request_with_invalid_user_id_in_request_body() {
	LoggerLoad.info("Admin sends Delete request for invalid user id");
	 endPoint = ConfigReader.getBaseUrl() + EndPoints.DELETE_USER.getEndpoint();
}

@When("Admin sends Delete Request with valid endpoint with invalid user id")
public void admin_sends_delete_request_with_valid_endpoint_with_invalid_user_id() {
	Response response = request.given()
			.pathParam("userId", "ABC#")
			.contentType("application/json").get(endPoint);
	scenarioContext.setResponse(response);
	LoggerLoad.info("response : "+ response.asString());
}

@Then("Admin receives {int} Not Found  Status with response body.")
public void admin_receives_not_found_status_with_response_body(Integer expStatusCode) {
	int actStatusCode = scenarioContext.getResponse().getStatusCode();
	LoggerLoad.info("actStatusCode : "+actStatusCode);
	ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
}

	
}
	