package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.programpojo;
import utilities.LoggerLoad;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import context.GlobalContext;



public class programAPI {
	
	programpojo Programpojo;
	
	
	public Response createPrograms(Map<String, String> rowData, String token) {
        programpojo Programpojo = new programpojo();
        Programpojo.setProgramDescription(rowData.get("ProgramDescription"));
        Programpojo.setProgramName(rowData.get("ProgramName"));
        Programpojo.setProgramStatus(rowData.get("ProgramStatus"));

        String jsonBody = Programpojo.getJson();
        LoggerLoad.info("Request Body: " + jsonBody);

        
        Response response = given()
                .header("Authorization", "Bearer " + token)  
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(EndPoints.SAVE_PROGRAM.getFullEndpoint());  

        LoggerLoad.info("Response Status Code: " + response.getStatusCode()); 
        LoggerLoad.info("Response Body: " + response.getBody().asPrettyString());

        return response;
    }


	    // GET: Get All Programs
	    public Response getAllPrograms(String token) {
	    	 
	        return given()
	            .header("Authorization", "Bearer " + token)
	            .when()
	            .get(EndPoints.GET_ALL_PROGRAMS.getFullEndpoint()); 
	    }

	    // GET: Get Program by ID
	    public Response getProgramById(String token, int programId) {
	        
	        return given()
	                .header("Authorization", "Bearer " + token)
	                .pathParam("programId", programId)
	                .when()
	                .get(EndPoints.GET_PROGRAM_BY_ID.getFullEndpoint());
	    }	
	    
	 // GET: Get AllPrograms with users
	    public Response getAllProgramwithusers(String token) {
	       

	        return given()
	        		 .header("Authorization", "Bearer " + token)
	 	            .when()
	 	            .get(EndPoints.GET_All_Programs_with_Users.getFullEndpoint()); 
	 	    }
	    
	//update:Byprogramid
	    
	    public Response putProgramById(Map<String, String> rowData, int programId, String token) {
	        programpojo Programpojo = new programpojo();
	        Programpojo.setProgramDescription(rowData.get("ProgramDescription"));
	        Programpojo.setProgramName(rowData.get("ProgramName"));
	        Programpojo.setProgramStatus(rowData.get("ProgramStatus"));

	        String jsonBody = Programpojo.getJson();
	        LoggerLoad.info("Request Body: " + jsonBody);

	       
	        Response response = given()
	                .header("Authorization", "Bearer " + token) 
	                .contentType(ContentType.JSON)
	                .body(jsonBody)
	                .pathParam("programId", programId)
	                .when()
	                .put(EndPoints.Update_Program_by_ProgramId.getFullEndpoint());  

	        LoggerLoad.info("Response Status Code: " + response.getStatusCode()); 
	        LoggerLoad.info("Response Body: " + response.getBody().asPrettyString());

	        return response;
	    }
	    
	  //update:ByprogramName
	    public Response updateProgramByName(Map<String, String> rowData, String token, String programName) {
	        programpojo Programpojo = new programpojo();
	        Programpojo.setProgramDescription(rowData.get("ProgramDescription"));
	        Programpojo.setProgramName(rowData.get("ProgramName"));
	        Programpojo.setProgramStatus(rowData.get("ProgramStatus"));

	        String jsonBody = Programpojo.getJson();
	        LoggerLoad.info("Request Body: " + jsonBody);

	        Response response= given()
	                .header("Authorization", "Bearer " + token)
	                .contentType(ContentType.JSON)
	                .body(jsonBody)
	                .pathParam("programName", programName)
	                .when()
	                .put(EndPoints.Update_Program_by_ProgramName.getFullEndpoint());
			return response;
	    }
	    
	    // DELETE: Delete Program by Name
	 
	    public Response deleteProgramByName(String token, String programName) {
	    	return given()
	                .header("Authorization", "Bearer " + token)
	                .pathParam("programName", programName)
	                .when()
	                .delete(EndPoints.Delete_Program_By_ProgramName.getFullEndpoint());
			
	    }
	   
	    
	    
	    

	    // DELETE: Delete Program by ID
	    public Response deleteProgramById(String token, int programId) {
	    	return given()
	                .header("Authorization", "Bearer " + token)
	                .pathParam("programId", programId)
	                .when()
	                .delete(EndPoints.Delete_Program_By_ProgramID.getFullEndpoint());
			
	    }

}




	
