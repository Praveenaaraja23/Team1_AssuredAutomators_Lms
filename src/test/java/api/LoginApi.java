package api;

import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import utilities.ConfigReader;

public class LoginApi {
	
	public static String login() {
		
		String bearerToken="";
		String loginBody="{"
				+ "  \"password\": \"Apiphase@2\",\n"
				+ "  \"userLoginEmailId\": \"sdetorganizer@gmail.com\"\n"
				+ "}";
			try
			{
				Response res = given()
				        .header("Content-Type","application/json")
				        .body(loginBody)
				       .when()
				        .post(ConfigReader.getProperty("baseUrl")+"/login");
				System.out.println(res.asPrettyString());
		        JsonPath gettoken = res.jsonPath();
		        bearerToken = gettoken.get("token");
		        System.out.println(res.statusCode());
		        Assert.assertEquals(res.statusCode(), 200);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}				
	        return bearerToken;
	}

}
