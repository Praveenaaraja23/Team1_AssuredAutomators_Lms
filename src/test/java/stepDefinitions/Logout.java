package stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;
import api.LoginApi;
import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;

public class Logout{
	
	RequestSpecification httpRequest;
	Response res;	
	private RequestSpecification request;
	private final ScenarioContext scenarioContext;
	String endPoint;

	public Logout(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for logout user.")
	public void admin_sets_authorization_to_bearer_token_for_logout_user() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
	}

     //logout with valid endpoint
	@When("Admin calls Get Https method with valid endpoint for logout")
	public void admin_calls_get_https_method_with_valid_endpoint_for_logout() {
		LoggerLoad.info("Admin sets GET request for logout with valid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.LOGOUT.getEndpoint();
		 Response response = request.given().contentType("application/json").get(endPoint);
			scenarioContext.setResponse(response);
			LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} OK  Status with logout response body")
	public void admin_receives_ok_status_with_logout_response_body(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//logout with invalid endpoint
	@When("Admin calls Get Https method with invalid endpoint for logout")
	public void admin_calls_get_https_method_with_invalid_endpoint_for_logout() {
		LoggerLoad.info("Admin sets GET request for logout with invalid endpoint");
		 endPoint = ConfigReader.getBaseUrl() + "ABC";
		 Response response = request.given().contentType("application/json").get(endPoint);
			scenarioContext.setResponse(response);
			LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} Not Found Status with logout response body")
	public void admin_receives_not_found_status_with_logout_response_body(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	
	//logout with No Auth
	@Given("Admin creates request with no Auth")
	public void admin_creates_request_with_no_auth() {
		 endPoint = ConfigReader.getBaseUrl() + EndPoints.LOGOUT.getEndpoint();
			request = RestAssured.given();
	}

	@When("Admin calls Get Https method with valid endpoint")
	public void admin_calls_get_https_method_with_valid_endpoint() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} Unauthorized Status with logout response body")
	public void admin_receives_unauthorized_status_with_logout_response_body(Integer expStatusCode) {
		int statusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("StatusCode : "+statusCode);
		ResponseValidator.validateStatusCode(statusCode, expStatusCode);
	}


}
	