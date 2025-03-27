package stepDefinitions;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import context.GlobalContext;
import endpoints.programAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import io.restassured.module.jsv.JsonSchemaValidator;

public class Program_SD {
	    
	    private Response response;
	    private String token;
	    private String sheetName;
	    private int Testcases;
	    private Map<String, String> rowData; 
		String programType;
		programAPI programApi;
		int programId;

		
		 
		public Program_SD() {
		     this.programApi = new programAPI();
		     
		 }  
	    
	    // Shared method to fetch data from ExcelExcelfilepath
	    private void fetchDataFromSheet(String sheetName, int Testcases) throws Exception {
	        this.sheetName = sheetName;
	        this.Testcases = Testcases;

	        // Fetch data dynamically using ExcelReader
	        List<Map<String, String>> excelData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Program");
	        this.rowData = excelData.get(Testcases - 1); // Store row data in class-level variable
	    }
	  
	    @Given("Admin logs in with valid credentials and Admin sets Authorization")
	    public void admin_logs_in_with_valid_credentials_and_admin_sets_authorization() {
	    	String token = GlobalContext.getToken();
			LoggerLoad.info("Token: " + token);
			
	  
	    }

	    //Post
	    
	    @When("User sends a POST request with data from {string} row {int}")
	    public void user_sends_a_post_request_with_data_from_row(String sheetName, Integer Testcases) throws Exception {
	    	
	    	// Fetch data from the sheet
	          fetchDataFromSheet(sheetName, Testcases);
			  response = programApi.createPrograms(rowData,GlobalContext.getToken());
			  LoggerLoad.info("Response Body: " + response.getBody().asString());
			
			   
			   if(response.getStatusCode() == 201) {
		           programId = response.jsonPath().getInt("programId");
		            String programName = response.jsonPath().getString("programName");
		            
		            GlobalContext.addProgramId(programId);
		            GlobalContext.addprogramName(programName);
		            
		        }
		          
		        }		  
	       
	    

	    @Then("Response status code should be displayed with with status Message")
	    public void response_status_code_should_be_displayed_with_with_status_message() {
	    	
	    	// Fetch expected status code and status line from the row data
	        int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
	       String expectedStatusMessage = rowData.get("Expected StatusMessage");
	       String expectedContentType = rowData.get("ExpectedContentType");
	       String actualcontentType = response.getHeader("Content-Type");
	       int actualstatusCode = response.getStatusCode();
	       String statusLine = response.getStatusLine();
	       String actualStatusMessage = statusLine.split(" ", 3)[2];

	        // Assert status code and status line
	        Assert.assertEquals( actualstatusCode,expectedStatusCode);
	        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);

	       
	       if(response.getStatusCode() == 201) {
	       JsonPath jsonPath = response.jsonPath();
	    // Data Type Validations
	       Assert.assertTrue(jsonPath.get("programId") instanceof Integer);
	       Assert.assertTrue(jsonPath.get("programName") instanceof String);
	       Assert.assertTrue(jsonPath.get("programDescription") instanceof String);
	       Assert.assertTrue(jsonPath.get("programStatus") instanceof String);
	       Assert.assertTrue(jsonPath.get("creationTime") instanceof String);
	       Assert.assertTrue(jsonPath.get("lastModTime") instanceof String);

	       // Data Value Validations
	       //Assert.assertEquals(jsonPath.getString("programDescription"), rowData.get("ProgramDescription"));
	       Assert.assertEquals(jsonPath.getString("programName"), rowData.get("ProgramName"));
	       Assert.assertEquals(jsonPath.getString("programStatus"), rowData.get("ProgramStatus"));
	       
	      
   		response.then().assertThat()
   		.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/Features/jsonschema/program.json")));
           
	       }
	      
	
	    } 
	    
	//GET:All Programs
	    
	    @When("User sends a GET request to fetch all programs from {string} row {int}")
	    public void user_sends_a_get_request_to_fetch_all_programs_from_row(String sheetName, Integer Testcases) throws Exception {
	       
	    	fetchDataFromSheet(sheetName, Testcases);
	    	response=programApi.getAllPrograms(GlobalContext.getToken());
	    }

	    @Then("Response status code should be displayed with status Message")
	    public void response_status_code_should_be_displayed_with_status_message() {
	    	
	    	 int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
		       String expectedStatusMessage = rowData.get("Expected StatusMessage");
		       
		       int actualstatusCode = response.getStatusCode();
		       String statusLine = response.getStatusLine();
		       String actualStatusMessage = statusLine.split(" ", 3)[2];

		       String expectedContentType = rowData.get("ExpectedContentType");
		       String actualcontentType = response.getHeader("Content-Type");
		       Assert.assertEquals(expectedContentType, actualcontentType);
		        Assert.assertEquals( actualstatusCode,expectedStatusCode);
		        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);


	       
	    }
	    
	  //GET:AllprogramsbyUsers    
	    @When("User sends a GET request to fetch all programs by users from {string} row {int}")
	    public void user_sends_a_get_request_to_fetch_all_programs_by_users_from_row(String sheetName, Integer Testcases) throws Exception {
	    	
	    	fetchDataFromSheet(sheetName, Testcases);
			response=programApi.getAllProgramwithusers(GlobalContext.getToken());
	    	
	    }
	   
//GET:programById
	@When("User sends a GET request to fetch program by valid ProgramId from {string} row {int}")
	public void user_sends_a_get_request_to_fetch_program_by_valid_program_id_from_row(String sheetName, Integer Testcases) throws Exception {
		fetchDataFromSheet(sheetName, Testcases);	
		response=programApi.getProgramById(GlobalContext.getToken(),GlobalContext.getProgramId(0));
	 
	}

	@Then("Response status code should be displayed with Response body containing program details")
	public void response_status_code_should_be_displayed_with_response_body_containing_program_details() {
		int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
	       String expectedStatusMessage = rowData.get("Expected StatusMessage");
	       
	       int actualstatusCode = response.getStatusCode();
	       String statusLine = response.getStatusLine();
	       String actualStatusMessage = statusLine.split(" ", 3)[2];

	      
	        Assert.assertEquals( actualstatusCode,expectedStatusCode);
	        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);
	        String expectedContentType = rowData.get("ExpectedContentType");
	        String actualcontentType = response.getHeader("Content-Type");
	        Assert.assertEquals(expectedContentType, actualcontentType);

	} 
	
	//update:ByprogramId
	
	@When("User sends a PUT request with programid {string} row {int}")
	public void user_sends_a_put_request_with_programid_row(String sheetName, Integer Testcases) throws Exception {
		
		fetchDataFromSheet(sheetName, Testcases);
		programId=GlobalContext.getProgramId(0);
		
		 response = programApi.putProgramById(rowData, programId, GlobalContext.getToken());
		 
		
		   if(response.getStatusCode() == 200) {
			   JsonPath jsonPath = new JsonPath(response.getBody().asString());
		        String updatedProgramName = jsonPath.getString("programName");
	             GlobalContext.addProgramId(programId);
	             GlobalContext.addprogramName(updatedProgramName);
	             
	             response.then().assertThat()
	        		.body(JsonSchemaValidator.matchesJsonSchema(new File("./src/test/resources/Features/jsonschema/program.json")));
	          
	        }		 
	}


	@Then("Response status code should be displayed and programId should be saved for validuser.")
	public void response_status_code_should_be_displayed_and_program_id_should_be_saved_for_validuser() {
		int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
	       String expectedStatusMessage = rowData.get("Expected StatusMessage");
	       
	       int actualstatusCode = response.getStatusCode();
	       String statusLine = response.getStatusLine();
	       String actualStatusMessage = statusLine.split(" ", 3)[2];
	       String expectedContentType = rowData.get("ExpectedContentType");
	       String actualcontentType = response.getHeader("Content-Type");

	        // Assert status code and status line
	        Assert.assertEquals(actualstatusCode,expectedStatusCode);
	        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);
	        Assert.assertEquals(expectedContentType, actualcontentType);
	        LoggerLoad.info("Actualstatuscode: " +actualstatusCode);
	        LoggerLoad.info("Actual Status Message: " + actualStatusMessage);
	       
	       
	   
	}
	
	//Update by ProgramName
	
	@When("User sends a PUT request with programName {string} row {int}")
	public void user_sends_a_put_request_with_program_name_row(String sheetName, Integer Testcases) throws Exception {
		
		fetchDataFromSheet(sheetName, Testcases);
		String programName = GlobalContext.getProgramName(0);
		response = programApi.updateProgramByName(rowData, GlobalContext.getToken(),programName);
		LoggerLoad.info("Response Body: " + response.getBody().asString());
        
        if(response.getStatusCode() == 200) {
	         
        	JsonPath jsonPath = new JsonPath(response.getBody().asString());
	        String updatedProgramName = jsonPath.getString("programName");
	      
             GlobalContext.addProgramId(programId);
             GlobalContext.addprogramName(updatedProgramName);
         
       }	
	   
	}

	@Then("Response status code should be displayed and programName should be saved for validuser.")
	public void response_status_code_should_be_displayed_and_program_name_should_be_saved_for_validuser() {
		
		int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
	       String expectedStatusMessage = rowData.get("Expected StatusMessage");
	       
	       int actualstatusCode = response.getStatusCode();
	       String statusLine = response.getStatusLine();
	       String actualStatusMessage = statusLine.split(" ", 3)[2];
	       String expectedContentType = rowData.get("ExpectedContentType");
	       String actualcontentType = response.getHeader("Content-Type");

	        // Assert status code and status line
	        Assert.assertEquals(actualstatusCode,expectedStatusCode);
	        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);
	        Assert.assertEquals(expectedContentType, actualcontentType);
	        LoggerLoad.info("Actualstatuscode: " +actualstatusCode);
	        LoggerLoad.info("Actual Status Message: " + actualStatusMessage);
	   
	}
	
	
//Deletebyprogramname
	@When("User sends a Delete request with programname {string} row {int}")
	public void user_sends_a_delete_request_with_programname_row(String sheetName, Integer Testcases) throws Exception {
		
		
		fetchDataFromSheet(sheetName, Testcases);

		String programName = GlobalContext.getProgramName(1);
		 response = programApi.deleteProgramByName(GlobalContext.getToken(), programName);
		 LoggerLoad.info("program Name deleted"+response.getBody().asString());
		 
	
	 
	}
	

	@When("User sends a Delete request with programid {string} row {int}")
	public void user_sends_a_delete_request_with_programid_row(String sheetName, Integer Testcases) throws Exception {
		
	
		
		fetchDataFromSheet(sheetName, Testcases);
		programId=GlobalContext.getProgramId(2);
	    response = programApi.deleteProgramById(GlobalContext.getToken(), programId);
	    LoggerLoad.info("program ID deleted"+response.getBody().asString());
	    

	}

	@Then("Response status code should be displayed and Data should be deleted.")
	public void response_status_code_should_be_displayed_and_data_should_be_deleted() {
		
		int expectedStatusCode = Integer.parseInt(rowData.get("Expected Statuscode"));
	       String expectedStatusMessage = rowData.get("Expected StatusMessage");
	       
	       int actualstatusCode = response.getStatusCode();
	       String statusLine = response.getStatusLine();
	       String actualStatusMessage = statusLine.split(" ", 3)[2];
	     

	        // Assert status code and status line
	        Assert.assertEquals(actualstatusCode,expectedStatusCode);
	        Assert.assertEquals(expectedStatusMessage,actualStatusMessage);
	        
	        LoggerLoad.info("Actualstatuscode: " +actualstatusCode);
	        LoggerLoad.info("Actual Status Message: " + actualStatusMessage);
	   
	}





	  
	}