package stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

//import RequestBodyRaw.LoginRequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
//import utilities.ReusableMethods;
//import utilities.ReusableVariables;
import api.LoginApi;
import context.ContextKey;
import context.TestContext;

public class LoginSteps{

	//ReusableMethods reuseMethods=new ReusableMethods();
	//ReusableVariables reuseVariables=new ReusableVariables();
	//LoginRequestBody loginReqbody=new LoginRequestBody();
	RequestSpecification httpRequest;
	Response res;
	
	
	private TestContext context;

	public LoginSteps(TestContext testContext) throws IOException
	{
		this.context= testContext;
	}
	
	//Login-with Authorization
   @Given("Admin creates request with valid credentials")
	public void admin_creates_request_with_valid_credentials() {	   
	   System.out.println("Admin sets HTTP request");
	   
	}

	@When("Admin calls Post Https method  with valid endpoint")
	public void admin_calls_post_https_method_with_valid_endpoint() {
		String token = LoginApi.login();
	    context.setContext(ContextKey.TOKEN.name(), token);
			
	}

	@Then("Admin receives {int} created with auto generated token")
	public void admin_receives_created_with_auto_generated_token(Integer int1) {
		Assert.assertNotNull(context.getContext(ContextKey.TOKEN.name()));
	}
	
}
	