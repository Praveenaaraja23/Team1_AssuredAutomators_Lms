package stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerLoad;
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
	
	//Login-with Authorization
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
	
}
	