package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.core.util.Assert;

import context.GlobalContext;
import context.ScenarioContext;
import endpoints.EndPoints;
import models.Class;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.ResponseValidator;

public class ClassSteps {
	private final ScenarioContext scenarioContext;
	private RequestSpecification request;
	String endPoint;
	
	public ClassSteps(ScenarioContext scenarioContext) throws IOException {
		this.scenarioContext = scenarioContext;
	}
	
	@Given("Admin sets Authorization to Bearer Token for class.")
	public void admin_sets_authorization_to_bearer_token_for_class() {
		String token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		request = RestAssured.given().header("Authorization", "Bearer " + token);
		
	}

	@Given("Admin creates POST Request for the LMS with request body")
	public void admin_creates_post_request_for_the_lms_with_request_body() {
	    LoggerLoad.info("Admin sets post request");
	    endPoint = ConfigReader.getBaseUrl() + EndPoints.CREATE_CLASS.getEndpoint();
	    	}

	@When("Admin calls POST request with endpoint from row {string}")
	public void admin_calls_post_request_with_endpoint_from_row(String scenarioName) {
		LoggerLoad.info("Admin sends post request");
		 LoggerLoad.info("Scenario Name: " + scenarioName);
			
			try {
				List<Map<String, String>> classData = ExcelReader.getData(ConfigReader.getProperty("excelPath"), "Class");
				for (Map<String, String> row : classData) {
					if (row.get("Scenario").equalsIgnoreCase(scenarioName)) {
						
						Class cls = new Class();
						int batch1=GlobalContext.getBatchId(0);
					//	cls.setbatchId(batch1);
					//  batchId from Excel (stored as String)
					String batchIdFromExcelStr = row.get("BatchId");
						// batchId with Data Chaining value
						int finalBatchId = batch1;

					if (batchIdFromExcelStr != null && !batchIdFromExcelStr.trim().isEmpty()) {
						    try {
						        //Convert Excel value to intger
						        finalBatchId = Integer.parseInt(batchIdFromExcelStr);
						        LoggerLoad.info("Batch ID from Excel: " + finalBatchId);
						       	                
						    } catch (NumberFormatException e) {
						        LoggerLoad.error("Invalid BatchId in Excel: " + batchIdFromExcelStr);
		        						        }
						}
					//  Set batchId as int
						cls.setbatchId(finalBatchId);
						LoggerLoad.info("Final BatchId used: " + finalBatchId);
						
						String classNoStr = row.get("ClassNo");
						System.out.println("classNo:"+classNoStr);	
						if (classNoStr == null || classNoStr.trim().isEmpty()) {
						    cls.setclassNo(0); // Default value if null or empty
						} else {
						    cls.setclassNo(Integer.parseInt(classNoStr));
						}
						cls.setclassDate(row.get("ClassDate"));
						cls.setclassTopic(row.get("ClassTopic"));
						cls.setclassStatus(row.get("ClassStatus"));
						cls.setclassComments(row.get("ClassComments"));
					    cls.setclassStaffId(row.get("ClassStaffId"));
					    cls.setclassDescription(row.get("ClassDescription"));
					    cls.setclassNotes(row.get("ClassNotes"));
					    cls.setclassRecordingPath(row.get("ClassRecordingPath"));
					    //cls.setclassScheduledDates(row.get("ClassScheduledDates"));
					    //List<String> scheduledDates = Arrays.asList(dates);
					    //cls.setclassScheduledDates(scheduledDates);
					    String scheduledDatesStr = row.getOrDefault("ClassScheduledDates", "");
		                List<String> scheduledDates = scheduledDatesStr.isEmpty() ? new ArrayList<>()
		                                            : Arrays.asList(scheduledDatesStr.split(","));
		                cls.setclassScheduledDates(scheduledDates);
		                //RequestSpecification request = RestAssured.given();
						
						Response response = request.given().contentType("application/json").body(cls).log().body()
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

	
	@Then("Admin receive success created status")
	public void admin_receive_success_created_status() {
		Response response = scenarioContext.getResponse();
		LoggerLoad.info("Admin calls post request");
		//LoggerLoad.info(csId);
		int expStatusCode = Integer.parseInt(scenarioContext.getRowData().get("ExpectedStatusCode"));
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		String expContentType = scenarioContext.getRowData().get("ContentType");
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
		ResponseValidator.validateContentType(scenarioContext.getResponse().getContentType(), expContentType);
		
		if (expStatusCode == 201 && actStatusCode == 201) {
			int classId = Integer.parseInt(scenarioContext.getResponse().jsonPath().getString("csId"));
			GlobalContext.addClassId(classId);
			LoggerLoad.info("classId :" + classId);
		
		 //JsonPath jsonPath = scenarioContext.getResponse().jsonPath();
			JsonPath jsonPath = response.jsonPath();
		  Map<String, String> expRow = scenarioContext.getRowData();
		// Validate schema
			ResponseValidator.validateJsonSchema(response, "Class_Schema.json");
		  
		// Validate Data type
		ResponseValidator.validateDataType(response, "csId", Integer.class);
		ResponseValidator.validateDataType(response, "batchId", Integer.class);
		ResponseValidator.validateDataType(response, "classNo", Integer.class);
		ResponseValidator.validateDataType(response, "classDate", String.class);
		ResponseValidator.validateDataType(response, "classTopic", String.class);
		ResponseValidator.validateDataType(response,  "classStaffId", String.class);
		ResponseValidator.validateDataType(response, "classDescription", String.class);
		ResponseValidator.validateDataType(response, "classComments", String.class);
		ResponseValidator.validateDataType(response,  "classNotes", String.class);
		ResponseValidator.validateDataType(response, "classRecordingPath", String.class);
		ResponseValidator.validateDateFormat(response, "classScheduledDates");
		//ResponseValidator.validateDataType(response, "classScheduledDates", Integer.class);
		// Validate Data
		ResponseValidator.validateData(jsonPath.getString("classTopic"), expRow.get("ClassTopic"));
		ResponseValidator.validateData(jsonPath.getString("classDescription"), expRow.get("ClassDescription"));
		ResponseValidator.validateData(jsonPath.getString("classComments"), expRow.get("ClassComments"));
		ResponseValidator.validateData(jsonPath.getString("classRecordingPath"), expRow.get("ClassRecordingPath"));
	  	ResponseValidator.validateData(jsonPath.getString("classNotes"), expRow.get("ClassNotes"));
	 
	}	
	}
	
	@Given("Admin creates GET Request")
	public void admin_creates_get_request() {
		    endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_All_CLASSES.getEndpoint();
		    scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
		}
	
@When("Admin sends HTTPS Request with endpoint")
	public void admin_sends_https_request_with_endpoint() {
	RequestSpecification request = scenarioContext.getRequest();
	Response response = request.given().contentType("application/json").log().body()
			.get(endPoint);
    scenarioContext.setResponse(response);
	LoggerLoad.info("Status Code: " + response.getStatusCode());
	if (response.getStatusCode() == 401) {
        LoggerLoad.error("Unauthorized Access: Check Bearer Token!");
    } else {
        LoggerLoad.info("Status Message: OK");
    }
		
 }

	@Then("Admin receives success OK Status with response body")
	public void admin_receives_success_ok_status_with_response_body() {
		Response response = scenarioContext.getResponse();
		//Status Code validation
		assertEquals("Expected status code 200!", 200, response.getStatusCode());
        LoggerLoad.info("Validated Status Code is 200 and Response body all classes " );
        //System.out.println("Response Body: \n" + response.getBody().asString());
        //Header Validation
		String contentType = response.header("Content-Type"); 
		System.out.println("Content-Type value: " + contentType);
        
      
	}
	
	@Given("Admin creates GET Request with Batchid")
	public void admin_creates_get_request_with_batchid() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSES_BY_BATCHID.getEndpoint();
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and Batchid")
	public void admin_sends_https_request_with_endpoint_and_batchid() {
		RequestSpecification request = scenarioContext.getRequest();
		int batchId = GlobalContext.getBatchId(0);
		Response response = request.given().contentType("application/json").log().body().pathParam("batchId", batchId).get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("Status Code: " + response.getStatusCode());
		}


	@Then("Admin receives success OK Status with response body for Batchid")
	public void admin_receives_success_ok_status_with_response_body_for_batchid() {
		Response response = scenarioContext.getResponse();
		//Status Code validation
		assertEquals("Expected status code 200!", 200, response.getStatusCode());
        LoggerLoad.info("Validated Status Code is 200 and Response body all classes " );
        //System.out.println("Response Body: \n" + response.getBody().asString());
        //Header Validation
		String contentType = response.header("Content-Type"); 
		System.out.println("Content-Type value: " + contentType);
	     //Data Validation
	     int expectedBatchId = GlobalContext.getBatchId(0); 
	     //compare above global context batch id with response batch id
		List<Integer> batchIds = response.jsonPath().getList("batchId");
	    assertTrue(batchIds.contains(expectedBatchId), "Batch ID not found in response!");
	 	 LoggerLoad.info("Response Body: " + response.getBody().asPrettyString());
	}

	@Given("Admin creates GET Request with Batchid for class recordings")
	public void admin_creates_get_request_with_batchid_for_class_recordings() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSRECORDINGS_BY_BATCHID.getEndpoint();
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and Batchid for class recordings")
	public void admin_sends_https_request_with_endpoint_and_batchid_for_class_recordings() {
		RequestSpecification request = scenarioContext.getRequest();
		int batchId = GlobalContext.getBatchId(0);
		Response response = request.given().contentType("application/json").log().body().pathParam("batchId", batchId).get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("Status Code: " + response.getStatusCode());
		}
	

	@Then("Admin receives success OK Status with response body class recordings with Batchid")
	public void admin_receives_success_ok_status_with_response_body_class_recordings_with_batchid() {
		Response response = scenarioContext.getResponse();
		//Status Code validation
		assertEquals("Expected status code 200!", 200, response.getStatusCode());
        LoggerLoad.info("Validated Status Code is 200 and Response body all classes " );
        //System.out.println("Response Body: \n" + response.getBody().asString());
        //Header Validation
		String contentType = response.header("Content-Type"); 
		System.out.println("Content-Type value: " + contentType);
		}
	@Given("Admin creates GET Request with classid")
	public void admin_creates_get_request_with_classid() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSDETAILS_BY_ID.getEndpoint();
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and classid")
	public void admin_sends_https_request_with_endpoint_and_classid() {
		RequestSpecification request = scenarioContext.getRequest();
		int classId = GlobalContext.getClassId(0);
		Response response = request.given().contentType("application/json").log().body().pathParam("classId", classId).get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("Status Code: " + response.getStatusCode());
	}

	@Then("Admin receives success OK Status with response body for classid")
	public void admin_receives_success_ok_status_with_response_body_for_classid() {
		Response response = scenarioContext.getResponse();
		//Status Code validation
		assertEquals("Expected status code 200!", 200, response.getStatusCode());
        LoggerLoad.info("Validated Status Code is 200 and Response body all classes " );
        //System.out.println("Response Body: \n" + response.getBody().asString());
        //Header Validation
		String contentType = response.header("Content-Type"); 
		System.out.println("Content-Type value: " + contentType);
	}
	@Given("Admin creates GET Request with classtopic")
	public void admin_creates_get_request_with_classtopic() {
		String classTopic = "Java";
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSES_BY_CLASSTOPIC.getEndpoint();
		endPoint = endPoint.replace("{classTopic}", classTopic);
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and classtopic")
	public void admin_sends_https_request_with_endpoint_and_classtopic() {
		RequestSpecification request = scenarioContext.getRequest();
		 Response response = request.given().contentType("application/json").log().all().get(endPoint);
		 scenarioContext.setResponse(response);
			LoggerLoad.info("Status Code: " + response.getStatusCode());
			
	}

	@Then("Admin receives success OK Status with response body for classtopic")
	public void admin_receives_success_ok_status_with_response_body_for_classtopic() {
		Response response = scenarioContext.getResponse();
		//Status Code validation
		assertEquals("Expected status code 200!", 200, response.getStatusCode());
        LoggerLoad.info("Validated Status Code is 200 and Response body all classes " );
        //System.out.println("Response Body: \n" + response.getBody().asString());
        //Header Validation
		String contentType = response.header("Content-Type"); 
		System.out.println("Content-Type value: " + contentType);
		//Data Validation
		String classTopicInResponse = response.jsonPath().getString("classTopic");
		 LoggerLoad.info("Class Topic in response: " + classTopicInResponse);
	}
	
	@Given("Admin creates GET Request all classes")
	public void admin_creates_get_request_all_classes() {
		endPoint = ConfigReader.getBaseUrl()+"!"+ EndPoints.GET_All_CLASSES.getEndpoint();
		  scenarioContext.setRequest(request); 
	}

	@When("Admin sends HTTPS Request with invalid endpoint to get all classes")
	public void admin_sends_https_request_with_invalid_endpoint_to_get_all_classes() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for all classes")
	public void admin_receives_status_with_error_message_for_all_classes(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class details by batchid")
	public void admin_creates_get_request_for_class_details_by_batchid() {
		endPoint = ConfigReader.getBaseUrl() +"oii"+ EndPoints.GET_CLASSES_BY_BATCHID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("batchId", GlobalContext.getBatchId(0));
		 // scenarioContext.setRequest(request); 
	}

	@When("Admin sends HTTPS Request with invalid endpoint to get class details by batchid")
	public void admin_sends_https_request_with_invalid_endpoint_to_get_class_details_by_batchid() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class details by batchid")
	public void admin_receives_status_with_error_message_for_class_details_by_batchid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class Recordings by batchid")
	public void admin_creates_get_request_for_class_recordings_by_batchid() {
		endPoint = ConfigReader.getBaseUrl() +"oii"+ EndPoints.GET_CLASSRECORDINGS_BY_BATCHID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("batchId", GlobalContext.getBatchId(0));
		  scenarioContext.setRequest(request);
	}

	@When("Admin sends HTTPS Request with invalid endpoint to get class Recordings by batchid")
	public void admin_sends_https_request_with_invalid_endpoint_to_get_class_recordings_by_batchid() {
		Response response = request.given().contentType("application/json").get(endPoint);
		request = RestAssured.given().contentType("application/json").pathParam("batchId", GlobalContext.getBatchId(0));
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class Recordings by batchid")
	public void admin_receives_status_with_error_message_for_class_recordings_by_batchid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class details by classid")
	public void admin_creates_get_request_for_class_details_by_classid() {
		endPoint = ConfigReader.getBaseUrl() +"oii"+ EndPoints.GET_CLASSDETAILS_BY_ID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("classId", GlobalContext.getClassId(0));
		scenarioContext.setRequest(request);
	}

	@When("Admin sends HTTPS Request with invalid endpoint to get class details by classid")
	public void admin_sends_https_request_with_invalid_endpoint_to_get_class_details_by_classid() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class details by classid")
	public void admin_receives_status_with_error_message_for_class_details_by_classid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for all classes by classtopic")
	public void admin_creates_get_request_for_all_classes_by_classtopic() {
		String classTopic = "Java";
		endPoint = ConfigReader.getBaseUrl() +"oii"+ EndPoints.GET_CLASSES_BY_CLASSTOPIC.getEndpoint();
		//endPoint = endPoint.replace("{classTopic}", classTopic);
		request = RestAssured.given().contentType("application/json").pathParam("classTopic", classTopic);;
		scenarioContext.setRequest(request);
	}

	@When("Admin sends HTTPS Request with invalid endpoint to get all classes by classtopic")
	public void admin_sends_https_request_with_invalid_endpoint_to_get_all_classes_by_classtopic() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for all classes by classtopic")
	public void admin_receives_status_with_error_message_for_all_classes_by_classtopic(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}
	@Given("Admin creates GET Request with invalid Batchid")
	public void admin_creates_get_request_with_invalid_batchid() {
		String invalidbatchId = "999999";
		endPoint = ConfigReader.getBaseUrl()+ EndPoints.GET_CLASSES_BY_INVALIDBATCHID.getEndpoint();
		endPoint = endPoint.replace("{invalidbatchId}", invalidbatchId);
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and invalid Batchid")
	public void admin_sends_https_request_with_endpoint_and_invalid_batchid() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for invalid Batchid")
	public void admin_receives_success_ok_status_with_response_body_for_invalid_batchid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request with invalid Batchid for class recordings")
	public void admin_creates_get_request_with_invalid_batchid_for_class_recordings() {
		String invalidbatchId = "999999";
		endPoint = ConfigReader.getBaseUrl()+ EndPoints.GET_CLASSRECORDINGS_BY_INVALIDBATCHID.getEndpoint();
		endPoint = endPoint.replace("{invalidbatchId}", invalidbatchId);
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and invalid Batchid for class recordings")
	public void admin_sends_https_request_with_endpoint_and_invalid_batchid_for_class_recordings() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message class recordings with invalid Batchid")
	public void admin_receives_success_ok_status_with_response_body_class_recordings_with_invalid_batchid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request with invalid classid")
	public void admin_creates_get_request_with_invalid_classid() {
		String invalidclassId = "999999";
		endPoint = ConfigReader.getBaseUrl()+ EndPoints.GET_CLASSDETAILS_BY_INVALIDID.getEndpoint();
		endPoint = endPoint.replace("{invalidclassId}", invalidclassId);
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and invalid classid")
	public void admin_sends_https_request_with_endpoint_and_invalid_classid() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for invalid classid")
	public void admin_receives_success_ok_status_with_response_body_for_invalid_classid(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request with invalid classtopic")
	public void admin_creates_get_request_with_invalid_classtopic() {
		String invalidclassTopic = "999999";
		endPoint = ConfigReader.getBaseUrl()+ EndPoints.GET_CLASSES_BY_INVALIDCLASSTOPIC.getEndpoint();
		endPoint = endPoint.replace("{invalidclassTopic}", invalidclassTopic);
		  scenarioContext.setRequest(request); 
		    LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request with endpoint and invalid classtopic")
	public void admin_sends_https_request_with_endpoint_and_invalid_classtopic() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for invalid classtopic")
	public void admin_receives_success_ok_status_with_response_body_for_invalid_classtopic(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request all classes without Authorization")
	public void admin_creates_get_request_all_classes_without_authorization() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_All_CLASSES.getEndpoint();
		request = RestAssured.given();
		//RequestSpecification request = RestAssured.given().contentType("application/json").header("Authorization", "");
	    //scenarioContext.setRequest(request); 
	    //LoggerLoad.info("Admin sets GET request with endpoint: " + endPoint);
	}

	@When("Admin sends HTTPS Request without Authorization to get all classes")
	public void admin_sends_https_request_without_authorization_to_get_all_classes() {
		Response response = request.given().contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		
//		 LoggerLoad.info("Request Headers: " + request.log().headers());
//		scenarioContext.setResponse(response);
//		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for all classes without Authorization")
	public void admin_receives_status_with_error_message_for_all_classes_without_authorization(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class details by batchid without Authorization")
	public void admin_creates_get_request_for_class_details_by_batchid_without_authorization() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSES_BY_BATCHID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("batchId",GlobalContext.getBatchId(0));
		scenarioContext.setRequest(request);
	}

	@When("Admin sends HTTPS Request without Authorization to get class details by batchid")
	public void admin_sends_https_request_without_authorization_to_get_class_details_by_batchid() {
		Response response = request.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class details by batchid without Authorization")
	public void admin_receives_status_with_error_message_for_class_details_by_batchid_without_authorization(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class Recordings by batchid  without Authorization")
	public void admin_creates_get_request_for_class_recordings_by_batchid_without_authorization() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSRECORDINGS_BY_BATCHID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("batchId",GlobalContext.getBatchId(0));
		scenarioContext.setRequest(request);
	}

	@When("Admin sends HTTPS Request without Authorization to get class Recordings by batchid")
	public void admin_sends_https_request_without_authorization_to_get_class_recordings_by_batchid() {
		Response response = request.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class Recordings by batchid without Authorization")
	public void admin_receives_status_with_error_message_for_class_recordings_by_batchid_without_authorization(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for class details by classid   without Authorization")
	public void admin_creates_get_request_for_class_details_by_classid_without_authorization() {
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSDETAILS_BY_ID.getEndpoint();
		request = RestAssured.given().contentType("application/json").pathParam("classId", GlobalContext.getClassId(0));
	}

	@When("Admin sends HTTPS Request  without Authorization to get class details by classid")
	public void admin_sends_https_request_without_authorization_to_get_class_details_by_classid() {
		Response response = request.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for class details by classid  without Authorization")
	public void admin_receives_status_with_error_message_for_class_details_by_classid_without_authorization(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}

	@Given("Admin creates GET Request for all classes by classtopic without Authorization")
	public void admin_creates_get_request_for_all_classes_by_classtopic_without_authorization() {
		String classTopic = "Java";
		endPoint = ConfigReader.getBaseUrl() + EndPoints.GET_CLASSES_BY_CLASSTOPIC.getEndpoint();
		endPoint = endPoint.replace("{classTopic}", classTopic);
		request = RestAssured.given();
	}

	@When("Admin sends HTTPS Request without Authorization to get all classes by classtopic")
	public void admin_sends_https_request_without_authorization_to_get_all_classes_by_classtopic() {
		Response response = request.contentType("application/json").get(endPoint);
		scenarioContext.setResponse(response);
		LoggerLoad.info("response : "+ response.asString());
	}

	@Then("Admin receives {int} status with error message for all classes by classtopic without Authorization")
	public void admin_receives_status_with_error_message_for_all_classes_by_classtopic_without_authorization(Integer expStatusCode) {
		int actStatusCode = scenarioContext.getResponse().getStatusCode();
		LoggerLoad.info("actStatusCode : "+actStatusCode);
		ResponseValidator.validateStatusCode(actStatusCode, expStatusCode);
	}



}
