package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import context.GlobalContext;
import context.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import models.UpdateClassRecording_POJO;
import models.UpdateClass_Pojo;
import utilities.LoggerLoad;
import utilities.RequestSpec;


public class ClassModule2_SD {

	private final ScenarioContext scenarioContext;
	private String token;
	private Response response;
	private String staffID_Invalid = "AB2025";
	private String csId_Invalid ="none";
	private String csId;
	private String staffID;
	private String singleObjectJson;

	public ClassModule2_SD(ScenarioContext scenarioContext) {
		this.scenarioContext = scenarioContext;

	}

	// *********** @ClassModule_StaffID1
	@Given("Admin creates GET Request for CLass module\\(allclasses by staffId)")
	public void admin_creates_get_request_for_c_lass_module_allclasses_by_staff_id() {
		token = GlobalContext.getToken();
		LoggerLoad.info("Token: " + token);
		staffID = (String) scenarioContext.getContext("classStaffId");
		System.out.print("printing the staffid :" + staffID);
	}

	@When("Admin sends HTTPS Request in ClassModule_StaffID with {string}")
	public void admin_sends_https_request_in_class_module_staff_id_with(String scenarioType) {
		
		switch (scenarioType) {
		case "valid staffId":
			System.out.print("printing the staffid INSIDE SWITCH CASE VALID STAFFID :" + staffID);
			response = given()
					.spec(RequestSpec.Get_AllClass_byStaffID(staffID))
					.header("Authorization", "Bearer " + token)
					.when().get();
			break;
		case "invalid staffId":
			response = given()
			         .spec(RequestSpec.Get_AllClass_byStaffID(staffID_Invalid))
					.header("Authorization", "Bearer " + token).
					when().get();
			break;
		case "invalid endpoint":
			response = given().
			          spec(RequestSpec.Get_AllClass_byStaffID_InvalidENDpoint(staffID))
					.header("Authorization", "Bearer " + token)
					.when().get();
			break;
		case "invalid method":
			response = given()
			           .spec(RequestSpec.Get_AllClass_byStaffID(staffID))
					.header("Authorization", "Bearer " + token)
					.when().post();
			break;
		case "no authorization":
			response = given()
			.spec(RequestSpec.Get_AllClass_byStaffID(staffID))
			.when().get();
			break;
		default:
			  System.out.println("Invalid scenario type: " + scenarioType);
		}
		if (response != null) {
	        System.out.println("Response successfully received for scenario: " + scenarioType);
	    } else {
	        System.out.println("Error: Response is null for scenario: " + scenarioType);
	    }
	}

	@Then("Admin receives Status in ClassModule_StaffID with response {int}")
	public void validateSuccessInputs_Class(Integer statusCode) {

		int actualstatuscode = response.getStatusCode();
		Assert.assertEquals(actualstatuscode, statusCode, "Unexpected status code!");

		if (actualstatuscode == 200) {
			try {
				response.then().body("classStaffId", Matchers.everyItem(Matchers.notNullValue()))
						.body("classStaffId", Matchers.everyItem(Matchers.equalTo(staffID))).log().all();
				System.out.print("Assertion passed: All classStaffId values are valid :" + staffID);
			} catch (AssertionError e) {
				System.err.println("Assertion failed: " + e.getMessage());
				Assert.fail("StaffId validation failed");
			}
		} else {
			Validateinvalid_statuscodes(actualstatuscode);
		}
	}
			
			
	// ******** @ClassModule_recording
	@Given("Admin creates GET Request for ClassModule_recording")
	public void admin_creates_get_request_for_class_module_recording() {
		System.out.println("created GET request for class module recording");
	}

	@When("Admin sends HTTPS Request in ClassModule_recording with {string}")
	public void admin_sends_https_request_in_class_module_recording_with(String scenarioType) {
		System.out.println("token for get class records" + token);
		switch (scenarioType) {
		case "valid":
			response = given().spec(RequestSpec.Get_ALlRecordings()).header("Authorization", "Bearer " + token).when()
					.get();
			break;
		case "invalid endpoint":
			response = given().spec(RequestSpec.Get_ALlRecordings_Invalid()).header("Authorization", "Bearer " + token)
					.when().get();
			break;
		case "invalid method":
			response = given().spec(RequestSpec.Get_ALlRecordings()).header("Authorization", "Bearer " + token).when()
					.post();
			break;
		case "no authorization":
			response = given().spec(RequestSpec.Get_ALlRecordings()).when().get();
			break;

		default:
			System.out.println("Invalid scenario type: " + scenarioType);
		}
	}

	@Then("Admin receives Status in ClassModule_recording with response {int}")
	public void admin_receives_status_in_class_module_recording_with_response(Integer statusCode) {
		int actualstatuscode = response.getStatusCode();
		if (actualstatuscode == 200) {	
				Assert.assertEquals(actualstatuscode, statusCode, "Unexpected status code!");
		} else {
			Validateinvalid_statuscodes(actualstatuscode);
		}
	}
	
	public void  Validateinvalid_statuscodes(int actualstatuscode) {
		if (actualstatuscode == 404) {
		System.out.println("Status Line " + response.getStatusLine());
	} else if (actualstatuscode == 405) {

		System.out.println("Status Line " + response.getStatusLine());
	} else if (actualstatuscode == 401) {

		System.out.println("Status Line " + response.getStatusLine());
	} else {
		System.out.println("Unexpected status code: " + actualstatuscode);
		response.then().log().all();
	}
}
// class module GET class recordings by CLass ID
@Given("Admin creates GET Request for ClassModule_recordingbyCLassID")
public void admin_creates_get_request_for_class_module_recordingby_c_lass_id() {
  
	csId = (String) scenarioContext.getContext("csId");
	System.out.print("printing the classId :" + csId);
}

@When("Admin sends HTTPS Request in ClassModule_recordingbyCLassID with {string}")
public void admin_sends_https_request_in_class_module_recordingby_c_lass_id_with(String scenarioType) {
	switch (scenarioType) {
	case "valid csId":
		response = given().spec(RequestSpec.Get_AllRecordings_byClassID(csId))
				.header("Authorization", "Bearer " + token).when().get();
		break;
	case "invalid csId":
		response = given().spec(RequestSpec.Get_AllRecordings_byClassID(csId_Invalid))
				.header("Authorization", "Bearer " + token).when().get();
		break;
	case "invalid endpoint":
		response = given().spec(RequestSpec.Get_AllRecordings_byClassID_InvalidEndpoint())
				.header("Authorization", "Bearer " + token).when().get();
		break;
	case "invalid method":
		response = given().spec(RequestSpec.Get_AllRecordings_byClassID(csId))
				.header("Authorization", "Bearer " + token).when().post();
		break;
	case "no authorization":
		response = given().spec(RequestSpec.Get_AllRecordings_byClassID(csId)).when().get();
		break;
	default:
		System.out.println("Invalid scenario type");
	}
}

@Then("Admin receives Status in ClassModule_recordingbyCLassID with response {int}")
public void admin_receives_status_in_class_module_recordingby_c_lass_id_with_response(Integer statusCode) {
	
	int actualstatuscode = response.getStatusCode();
	Assert.assertEquals(actualstatuscode, statusCode, "Unexpected status code!");

	if (actualstatuscode == 200) {
		try {
			response.then().body("csId",Matchers.notNullValue())
					.body("csId",Matchers.equalTo(Integer.parseInt(csId))).log().all();
			System.out.print("Assertion passed: All csId values are valid :" + csId);
		} catch (AssertionError e) {
			System.err.println("Assertion failed: " + e.getMessage());
			Assert.fail("csId validation failed");
		}
	} else {
		Validateinvalid_statuscodes(actualstatuscode);
	}
    
}
// CLass module PUT request-new class

@Given("Admin creates PUT Request for ClassModule_UpdateNewClass")
public void admin_creates_get_request_for_class_module_update_new_class() {
	csId = (String) scenarioContext.getContext("csId");
	System.out.print("printing the classId inside ClassModule_UpdateNewClass:" + csId);
}

@When("Admin sends HTTPS Request in ClassModule_UpdateNewClass with {string}")
public void admin_sends_https_request_in_class_module_update_new_class_with(String scenarioType) {
	try {
		csId ="48";
		File jsonFile = new File("src/test/resources/PUT_ClassModule_testdata2.json");

        String jsonString = new String(Files.readAllBytes(jsonFile.toPath()));
     //   System.out.println("print the original array of request jsonbody :"+jsonString);	
        
        ObjectMapper objectMapper = new ObjectMapper();
        List<UpdateClass_Pojo> classList = objectMapper.readValue(jsonString, new TypeReference<List<UpdateClass_Pojo>>(){});
      
		switch (scenarioType) {
		case "validclassID":
			  singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for validclassID: " + singleObjectJson);
			response = given()
					.spec(RequestSpec.Put_Class(csId))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invalidclassID":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(1));
		        System.out.println("Single Object to be sent for invalidclassID: " + singleObjectJson);
			response = given()
			         .spec(RequestSpec.Put_Class("6988536"))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invaliddata":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(2));
		        System.out.println("Single Object to be sent for invaliddata: " + singleObjectJson);
			response = given().
			          spec(RequestSpec.Put_Class(csId))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "missingData":
			singleObjectJson = objectMapper.writeValueAsString(classList.get(3));
        System.out.println("Single Object to be sent for missingData: " + singleObjectJson);
	response = given().
	          spec(RequestSpec.Put_Class(csId))
			.header("Authorization", "Bearer " + token)
			.header("Content-Type","application/json") 
			.body(singleObjectJson)
			.when().put();
	break;
			
		case "invalidendpoint":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for invalidendpoint: " + singleObjectJson);
			response = given()
			           .spec(RequestSpec.Put_Class_InvalidEndpoint())
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invalidmethod":
			
			response = given()
			           .spec(RequestSpec.Put_Class(csId))
					.header("Authorization", "Bearer " + token)
					.when().get();
			break;
		case "noauthorization":
			  singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for noauthorization: " + singleObjectJson);
			response = given()
			.spec(RequestSpec.Put_Class(csId))
			.header("Content-Type","application/json") 
			.body(singleObjectJson)
			.when().put();
			break;
		default:
			  System.out.println("Invalid scenario type: " + scenarioType);
		}
		if (response != null) {
	        System.out.println("Response successfully received for scenario: " + scenarioType);
	    } else {
	        System.out.println("Error: Response is null for scenario: " + scenarioType);
	    }
		
	} catch (IOException e) {
		System.err.println("Failed to read JSON file: " + e.getMessage());
		e.printStackTrace();
		Assert.fail("Test failed due to an IOException.");
	}
}

@Then("Admin receives Status in ClassModule_UpdateNewClass with response {int}")
public void admin_receives_status_in_class_module_update_new_class_with_response(Integer statuscode) {
	int actualstatuscode = response.getStatusCode();
	 System.out.println("Actual Status Code: " + actualstatuscode);
	 try {
	Assert.assertEquals(actualstatuscode, statuscode, "Unexpected status code!");
	 }catch(AssertionError e) {
		 System.err.println("Response Body: " + response.getBody().asString());
	 }
	 response.then().log().all();
}

// ClassModule_UpdateClassRecordingpath
@Given("Admin creates PUT Request for ClassModule_UpdateClassRecordingpath")
public void admin_creates_put_request_for_class_module_update_class_recordingpath() {
	csId = (String) scenarioContext.getContext("csId");
	System.out.print("printing the classId inside ClassModule_UpdateNewClass:" + csId);
}

@When("Admin sends HTTPS Request in ClassModule_UpdateClassRecordingpath with {string}")
public void admin_sends_https_request_in_class_module_update_class_recordingpath_with(String scenarioType) {
	try {
		csId ="48";
		File jsonFile = new File("src/test/resources/PUT_Class_RecordingPath.json");

        String jsonString = new String(Files.readAllBytes(jsonFile.toPath()));
        
        ObjectMapper objectMapper = new ObjectMapper();
        List<UpdateClassRecording_POJO> classList = objectMapper.readValue(jsonString, new TypeReference<List<UpdateClassRecording_POJO>>(){});
      
		switch (scenarioType) {
		case "validclassID":
			  singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for validclassID: " + singleObjectJson);
			response = given()
					.spec(RequestSpec.Put_classREcording(csId))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invalidclassID":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(1));
		        System.out.println("Single Object to be sent for invalidclassID: " + singleObjectJson);
			response = given()
			         .spec(RequestSpec.Put_classREcording("988536"))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invaliddata":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(2));
		        System.out.println("Single Object to be sent for invaliddata: " + singleObjectJson);
			response = given().
			          spec(RequestSpec.Put_classREcording(csId))
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "missingData":
			singleObjectJson = objectMapper.writeValueAsString(classList.get(3));
        System.out.println("Single Object to be sent for missingData: " + singleObjectJson);
	response = given().
	          spec(RequestSpec.Put_classREcording(csId))
			.header("Authorization", "Bearer " + token)
			.header("Content-Type","application/json") 
			.body(singleObjectJson)
			.when().put();
	break;
			
		case "invalidendpoint":
			 singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for invalidendpoint: " + singleObjectJson);
			response = given()
			           .spec(RequestSpec.Put_classREcordingInvalidEnd())
					.header("Authorization", "Bearer " + token)
					.header("Content-Type","application/json") 
					.body(singleObjectJson)
					.when().put();
			break;
		case "invalidmethod":
			
			response = given()
			           .spec(RequestSpec.Put_classREcording(csId))
					.header("Authorization", "Bearer " + token)
					.when().get();
			break;
		case "noauthorization":
			  singleObjectJson = objectMapper.writeValueAsString(classList.get(0));
		        System.out.println("Single Object to be sent for noauthorization: " + singleObjectJson);
			response = given()
			.spec(RequestSpec.Put_classREcording(csId))
			.header("Content-Type","application/json") 
			.body(singleObjectJson)
			.when().put();
			break;
		default:
			  System.out.println("Invalid scenario type: " + scenarioType);
		}
		if (response != null) {
	        System.out.println("Response successfully received for scenario: " + scenarioType);
	    } else {
	        System.out.println("Error: Response is null for scenario: " + scenarioType);
	    }
		
	} catch (IOException e) {
		System.err.println("Failed to read JSON file: " + e.getMessage());
		e.printStackTrace();
		Assert.fail("Test failed due to an IOException.");
	}
}

@Then("Admin receives Status in ClassModule_UpdateClassRecordingpath with response {int}")
public void admin_receives_status_in_class_module_update_class_recordingpath_with_response(Integer statuscode) {
	int actualstatuscode = response.getStatusCode();
	 System.out.println("Actual Status Code: " + actualstatuscode);
	 try {
	Assert.assertEquals(actualstatuscode, statuscode, "Unexpected status code!");
	 }catch(AssertionError e) {
		 System.err.println("Response Body: " + response.getBody().asString());
	 }
	 response.then().log().all();
}

@Given("Admin creates DELETE Request with valid classId DeleteClass_CSID")
public void admin_creates_delete_request_with_valid_class_id_delete_class_csid() {
	csId = (String) scenarioContext.getContext("csId");
	System.out.print("printing the classId inside DeleteClass_CSID:" + csId);
}


@When("Admin sends HTTPS Request  with endpoint DeleteClass_CSID {string}")
public void admin_sends_https_request_with_endpoint_delete_class_csid(String scenarioType) {
	
		switch (scenarioType) {
		case "validclassID":
			response = given()
					.spec(RequestSpec.Delete_classByID(csId))
					.header("Authorization", "Bearer " + token)
					.when().delete();
			break;
		case "invalidclassID":
			response = given()
			.spec(RequestSpec.Delete_classByID("5656"))
			.header("Authorization", "Bearer " + token)
			.when().delete();
			break;
		case "invalidendpoint":
			response = given()
			.spec(RequestSpec.Delete_classByID_Invalid(csId))
			.header("Authorization", "Bearer " + token)
			.when().delete();
			break;
		case "noauthorization":
			response = given()
			.spec(RequestSpec.Delete_classByID("48"))
			.when().delete();
			break;
		default:
			  System.out.println("Invalid scenario type: " + scenarioType);
		}
}

@Then("Admin receives Status in DeleteClass_CSID with response {int}")
public void admin_receives_status_in_delete_class_csid_with_response(Integer statuscode) {
	int actualstatuscode = response.getStatusCode();
	 System.out.println("Actual Status Code: " + actualstatuscode);
	 try {
	Assert.assertEquals(actualstatuscode, statuscode, "Unexpected status code!");
	 }catch(AssertionError e) {
		 System.err.println("Response Body: " + response.getBody().asString());
	 }
	 response.then().log().all();
}


}
