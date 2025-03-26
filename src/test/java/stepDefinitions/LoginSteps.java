package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;
import api.LoginApi;
import context.GlobalContext;
import context.ScenarioContext;

public class LoginSteps{
	
	RequestSpecification httpRequest;
	Response res;	
	private final ScenarioContext scenarioContext;

	public LoginSteps(ScenarioContext scenarioContext) throws IOException
	{
		this.scenarioContext= scenarioContext;
	}
	
	//Login-with valid credentials and valid endpoint
   @Given("Admin creates request with valid credentials")
	public void admin_creates_request_with_valid_credentials() {	
	   System.out.println("Test123");
	   LoggerLoad.info("Admin sets HTTP request");
	   
	}

	@When("Admin calls Post Https method  with valid endpoint")
	public void admin_calls_post_https_method_with_valid_endpoint() {
		String token = LoginApi.login();
		GlobalContext.setToken(token);
		//scenarioContext.setContext(ContextKey.TOKEN.name(), token);
			
	}

	@Then("Admin receives {int} created with auto generated token")
	public void admin_receives_created_with_auto_generated_token(Integer int1) {
		//System.out.println("Token: "+ scenarioContext.getContext(ContextKey.TOKEN.name()));
		//Assert.assertNotNull(scenarioContext.getContext(ContextKey.TOKEN.name()));
		Assert.assertNotNull(GlobalContext.getToken());
		
	}
	
	//Login-with invalid endpoint
	@When("Admin calls Post Https method  with invalid endpoint for auto generated token")
	public void admin_calls_post_https_method_with_invalid_endpoint_for_auto_generated_token() {
		String loginBody="{"
				+ "  \"password\": \"Apiphase@2\",\n"
				+ "  \"userLoginEmailId\": \"sdetorganizer@gmail.com\"\n"
				+ "}";
	      
		Response res = given()
		        .header("Content-Type","application/json")
		        .body(loginBody)
		       .when()
		        .post(ConfigReader.getProperty("baseUrl")+"/login123");
		scenarioContext.setResponse(res);
		LoggerLoad.info("response : "+ res.asString());
		
	}
	
	@Then("Admin receives {int} Unauthorized in response body for auto generated token")
	public void admin_receives_unauthorized_in_response_body_for_auto_generated_token(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	//Login-with invalid credentails
	@When("Admin calls Post Https method  with invalid credentails for auto generated token")
	public void admin_calls_post_https_method_with_invalid_credentails_for_auto_generated_token() {
		String loginBody="{"
				+ "  \"password\": \"Apiphase@25\",\n"
				+ "  \"userLoginEmailId\": \"sdetorganizers@gmail.com\"\n"
				+ "}";
	      
		Response res = given()
		        .header("Content-Type","application/json")
		        .body(loginBody)
		       .when()
		        .post(ConfigReader.getProperty("baseUrl")+"/login");
		scenarioContext.setResponse(res);
		LoggerLoad.info("response : "+ res.asString());
		
		
	}
	
	@Then("Admin receives {int} Not Found Status in response body for auto generated token")
	public void admin_receives_not_found_status_in_response_body_for_auto_generated_token(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	    
	}
	
	

}
	